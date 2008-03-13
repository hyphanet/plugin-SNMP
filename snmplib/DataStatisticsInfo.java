package plugins.SNMP.snmplib;

import freenet.io.comm.IOStatisticCollector;

public class DataStatisticsInfo implements DataFetcher {
	private String OID;
	int blocks;
	boolean in;
	private final IOStatisticCollector collector;
	
	public DataStatisticsInfo(int blocks, boolean in, IOStatisticCollector collector) {
		this.OID = "1.1." + blocks + '.' + (in?"1":"0");
		//System.err.println("adding: " + this.OID);
		this.in = in;
		this.blocks = blocks;
		this.collector = collector;
	}
	
	public String getSNMPOID() {
		//System.err.println("        " + this.OID);
		return OID;
	}

	public Object getSNMPData() {
		if (blocks == 0) {
			long io[] = collector.getTotalIO();
			return new SNMPCounter32(io[in?1:0]);
		}
		// else sum all fields up to <blocks>
		int res = 0;
		int stats[][] = collector.getTotalStatistics();
		for (int i = 0 ; i < blocks ; i++)
			res += stats[i][in?1:0];
		
		return new Long(res);
	}
}
