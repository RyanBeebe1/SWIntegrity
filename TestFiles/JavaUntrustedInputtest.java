class Test {



    public static void main(String args[]) {
        String ip = request.getRemoteAddr();
        InetAddress addr = InetAddress.getByName(ip);
        if (addr.getCanonicalHostName().endsWith("trustme.com")) {
            trusted = true;
        }

        Cookie[] cookies = request.getCookies();
        for (int i =0; i< cookies.length; i++) {
            Cookie c = cookies[i];
            if (c.getName().equals("authenticated") && Boolean.TRUE.equals(c.getValue())) {
                authenticated = true;
            }
        }

        Cookie[] cookies = request.getCookies();
        for (int i =0; i< cookies.length; i++) {
            Cookie c = cookies[i];
            if (c.getName().equals("role")) {
                userRole = c.getValue();
            }
        }
    }
}