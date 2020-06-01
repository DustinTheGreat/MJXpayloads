
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
