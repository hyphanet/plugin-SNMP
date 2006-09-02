package plugins.SNMP.snmplib;

import freenet.config.IntCallback;
import freenet.config.InvalidConfigValueException;

public class SNMPPortNumberCallback implements IntCallback {
	
	public SNMPPortNumberCallback() {
	}
	
	public int get() {
		return SNMPAgent.getSNMPAgent().getSNMPort();
	}

	public void set(int val) throws InvalidConfigValueException {
		if(val != get()) {
			SNMPAgent.setSNMPPort(val);
		}
	}
}