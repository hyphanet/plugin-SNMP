package plugins;

import freenet.config.Config;
import freenet.config.SubConfig;
import freenet.pluginmanager.*;
import freenet.support.Logger;
import plugins.snmplib.*;


public class SNMP implements FredPlugin{
	boolean goon=true;
	PluginRespirator pr;
	private int port;
	private String bindto;

	public void terminate() {
		Logger.normal(this,"Stopping SNMP");
		SNMPAgent.stopSNMPAgent();
		goon=false;
	}
	
	public int getPort(){
		return port;
	}
	
	public String getBindto(){
		return bindto;
	}

	public void runPlugin(PluginRespirator pr) {
		this.pr = pr;
		SNMPAgent.stopSNMPAgent();
		
		try{
		Config c=pr.getNode().config;
		SubConfig sc=new SubConfig("plugins.snmp",c);
		sc.register("port", 4000,2, true, "SNMP port number", "SNMP port number", new SNMPPortNumberCallback());
		sc.register("bindto", "127.0.0.1", 2, true, "Ip address to bind to", "Ip address to bind the SNMP server to", new SNMPBindtoCallback());
		
		bindto=sc.getString("bindto");
		port=sc.getInt("port");
		
		SNMPAgent.setSNMPPort(port);
        System.out.println("Starting SNMP server on "+bindto+":"+port);
        SNMPStarter.initialize();
		Logger.normal(this,"Starting SNMP server on "+bindto+":"+port);
		
		sc.finishedInitialization();
		while(goon){};
		}catch (IllegalArgumentException e){
			Logger.error(this, "Error loading SNMP server");
		}
	}
}

