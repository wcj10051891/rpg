package premain;
import java.lang.instrument.Instrumentation;
public class InstrumentAgent {
	public static Instrumentation instrument;
	public static void agentmain(String agentArgs, Instrumentation inst) {
		InstrumentAgent.instrument = inst;
	}
	public static void premain(String agentArgs, Instrumentation inst) {
		InstrumentAgent.instrument = inst;
	}
}