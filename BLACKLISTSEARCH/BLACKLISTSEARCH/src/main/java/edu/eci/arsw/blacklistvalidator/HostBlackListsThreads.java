package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HostBlackListsThreads extends Thread {

    int Count = 0;

    private static final int BLACK_LIST_ALARM_COUNT=5;

    private String ipAddress;
    private int vectorInicial,vectorFinal;
    private List<Integer> blackListOcurrences;

    @Override
    public void run() {
        blackListOcurrences = checkHost(ipAddress, vectorInicial,vectorFinal);
        System.out.println("El Host a sido encontrado en las siguientes Blacl List:"+blackListOcurrences);
        Ocurrences();
    }



    public HostBlackListsThreads(String ipaddress, int vectorInicial, int vectorFinal) {
        ipAddress = ipaddress;
        this.vectorInicial = vectorInicial;
        this.vectorFinal = vectorFinal;
    }


    public List<Integer> checkHost(String ipaddress, int vectorInicial, int vectorFinal){

        LinkedList<Integer> blackListOcurrences=new LinkedList<>();

        int ocurrencesCount=0;

        HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();

        int checkedListsCount=vectorInicial;

        for (int i=vectorInicial;i< vectorFinal && ocurrencesCount<BLACK_LIST_ALARM_COUNT;i++){
            checkedListsCount++;

            if (skds.isInBlackListServer(i, ipaddress)){

                blackListOcurrences.add(i);

                ocurrencesCount++;
                Count = ocurrencesCount;
            }
        }

        if (ocurrencesCount>=BLACK_LIST_ALARM_COUNT){
            skds.reportAsNotTrustworthy(ipaddress);
        }
        else{
            skds.reportAsTrustworthy(ipaddress);
        }

        LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{checkedListsCount, skds.getRegisteredServersCount()});
        System.out.println(blackListOcurrences.size());
        return blackListOcurrences;
    }


    private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());

    public void Ocurrences(){
        System.out.printf("%d",Count);
    }

}