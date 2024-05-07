package pl.zzpj.solid.ocp.usa.violation;

public class USASpeedLimitFines {

	private static final int SC_MAX_SPEED = 0;
	private static final int AL_MAX_SPEED = 0;
	private static final int GA_MAX_SPEED = 0;

	public double calcualateSpeedLimitFine(String stateCode, int speed) {

		double fine = 0;
		switch (stateCode) {
		case "SC": {
			if (speed > SC_MAX_SPEED) {
				// calculate...
			}
			return fine;
		}
		case "AL": {
			if (speed > AL_MAX_SPEED) {
				// calculate...
			}
			return fine;
		}
		case "GA": {
			if (speed > GA_MAX_SPEED) {
				// calculate...
			}
			return fine;
		} 

		}
		return 0.0;

	}

}
