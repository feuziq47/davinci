package davinchiSnC;

public class protocol {
	// Login & Sign Up task
	public static final String loginSignal =		"[LOGIN]";
	public static final String loginSuccess = 		"[LGSUC]";
	public static final String loginFailure =	 	"[LGFAL]";
	public static final String signupSingal =  		"[SGNUP]";
	public static final String signupSuccess = 		"[SGSUC]";
	public static final String idCheckSignal = 		"[IDCHK]";
	public static final String idDuplicated = 		"[IDDUP]";
	public static final String idNotDuplicated = 	"[IDNDP]";
	
	// Room Info task ( 3 char + room_num + msg )
	public static final String chatSignal = 			"000";
	public static final String roomChangeSignal = 		"001";
	public static final String roomEnterSignal = 		"002";
	public static final String roomEnterSuccessSignal = "003";
	public static final String roomExitSignal = 		"004";
	public static final String roomExitSuccessSignal = 	"005";
	public static final String userEnterSignal		 = 	"006";
	public static final String userExitSignal		 = 	"007";

	// Room Info task ( 3 char + msg )
	public static final String refereeAnounce		 = 	"008";	
	public static final String gameStartSignal		 = 	"009";
	public static final String gameReadySignal		 = 	"010";
	public static final String gameReadyTrue		 = 	"011";
	public static final String gameReadyFalse		 = 	"012";
	public static final String gameAddBlockSignal	 = 	"013";
	public static final String gameBlockSendSignal	 = 	"014";
	public static final String gameBlockCorrect		 = 	"015";
	public static final String gamePlayerDead		 = 	"016";
	public static final String gameEndSignal		 = 	"017";
	
	// In Game task
}
