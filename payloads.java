/* Follow */
    public void start() {
        this.has_result = false;
        new Thread(new Runnable() {
            public void run() {
                byte[] arr = new byte[5];
                arr[0] = FollowBean.header;
                arr[1] = FollowBean.entry_data_msgid;
                arr[2] = FollowBean.exit_data_msgid;
                arr[4] = 1;
                arr[3] = (byte) BaseBean.getCheckSum(arr, arr.length);
                CMDSendUtil.send(arr, 5, 200);
                FollowBean.this.checkTimeoutResult(4, 3);
            }
        }).start();
    }

    public void stop() {
        this.has_result = false;
        new Thread(new Runnable() {
            public void run() {
                byte[] arr = new byte[5];
                arr[0] = FollowBean.header;
                arr[1] = FollowBean.exit_data_msgid;
                arr[2] = FollowBean.exit_data_msgid;
                arr[4] = 1;
                arr[3] = (byte) BaseBean.getCheckSum(arr, arr.length);
                CMDSendUtil.send(arr, 5, 200);
                FollowBean.this.checkTimeoutResult(5, 2);
            }
        }).start();
    }

    public void stop_now() {
        byte[] arr = new byte[5];
        arr[0] = header;
        arr[1] = exit_data_msgid;
        arr[2] = exit_data_msgid;
        arr[4] = 1;
        arr[3] = (byte) getCheckSum(arr, arr.length);
        CMDSendUtil.send(arr, 2, 100);
    }
/* Circle */

    public void start() {
        this.has_result = false;
        new Thread(new Runnable() {
            public void run() {
                byte[] arr = new byte[5];
                arr[0] = CircleModeBean.header;
                arr[1] = CircleModeBean.entry_data_msgid;
                arr[2] = 5;
                arr[4] = 1;
                arr[3] = (byte) BaseBean.getCheckSum(arr, arr.length);
                CMDSendUtil.send(arr, 5, 200);
            }
        }).start();
    }

    public void stop() {
        this.has_result = false;
        new Thread(new Runnable() {
            public void run() {
                byte[] arr = new byte[5];
                arr[0] = CircleModeBean.header;
                arr[1] = CircleModeBean.exit_data_msgid;
                arr[2] = 5;
                arr[4] = 1;
                arr[3] = (byte) BaseBean.getCheckSum(arr, arr.length);
                CMDSendUtil.send(arr, 5, 200);
                CircleModeBean.this.checkTimeoutResult(7, 2);
            }
        }).start();
    }

    public void stop_now() {
        byte[] arr = new byte[5];
        arr[0] = header;
        arr[1] = exit_data_msgid;
        arr[2] = 5;
        arr[4] = 1;
        arr[3] = (byte) getCheckSum(arr, arr.length);
        CMDSendUtil.send(arr, 2, 100);
    }
/* Waypoint */







/* Send */


public class CMDSendUtil {
    private static final String IP = "192.168.99.1";
    private static final int PORT = 9001;
    private static String TAG = "CMDSendUtil";
    private static boolean isConnected = false;

    public static boolean isConnected() {
        return isConnected;
    }

    public static boolean connect() {
        boolean z = true;
        int ok = UAVNative.RVSTConnect(IP, 9001, 2, 1);
        UAVNative.RVSTSubscribe(1);
        UAVNative.RVSTCreatSubscribeDataThread();
        if (ok != 0) {
            z = false;
        }
        isConnected = z;
        return z;
    }

    public static void disconnect() {
        UAVNative.RVSTDisConnect();
        UAVNative.RVSTDestoryRecvSubscribeDataThread();
        isConnected = false;
    }

    public static boolean send(int[] cmd) {
        boolean z = false;
        synchronized (TAG) {
            System.out.println("CMD:" + Arrays.toString(cmd));
            if (!isConnected) {
                System.err.println("send err 未连接");
            } else if (UAVNative.RVSTSendUAVData(cmd.length, cmd) == 0) {
                z = true;
            }
        }
        return z;
    }