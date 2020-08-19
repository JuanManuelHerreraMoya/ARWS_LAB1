/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class HostBlackListsValidator {

    private static final int BLACK_LIST_ALARM_COUNT=5;
    private int numeroThreads;
    private String ipaddress;
    private List<Integer>blackListOcurrences;

    /**
     * Check the given host's IP address in all the available black lists,
     * and report it as NOT Trustworthy when such IP was reported in at least
     * BLACK_LIST_ALARM_COUNT lists, or as Trustworthy in any other case.
     * The search is not exhaustive: When the number of occurrences is equal to
     * BLACK_LIST_ALARM_COUNT, the search is finished, the host reported as
     * NOT Trustworthy, and the list of the five blacklists returned.
     * @param ipaddress suspicious host's IP address.
     * @return  Blacklists numbers where the given host's IP address was found.
     */
    public HostBlackListsValidator(String ipaddress, int numeroThreads) {
        this.numeroThreads = numeroThreads;
        this.ipaddress=ipaddress;
        blackListOcurrences= checkHost(ipaddress,numeroThreads);
    }

    public List<Integer> checkHost(String ipaddress, int numeroThreads){

        LinkedList<HostBlackListsThreads> hostBlackList_p=new LinkedList<>();
        
        HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();

        int countCheckedLists=0;
        int divicionRangos = skds.getRegisteredServersCount()/numeroThreads;
        System.out.println(divicionRangos);
        int []partesRango = new int [numeroThreads+1];
        for(int i = 0; i <= numeroThreads; i++){
            partesRango[i]=i*divicionRangos;
        }
        System.out.println(Arrays.toString(partesRango));
        for (int i=1; i <= partesRango.length-1; i++){
            HostBlackListsThreads verificationThread = new HostBlackListsThreads(ipaddress,partesRango[i-1],partesRango[i]-1);
            hostBlackList_p.add(verificationThread);
        }
        for (int i=0; i < partesRango.length-1; i++){
            hostBlackList_p.get(i).start();
        }

        
        //LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{countCheckedLists, skds.getRegisteredServersCount()});
        
        return blackListOcurrences;
    }
    
    
    private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());
    
    
    
}
