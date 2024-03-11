package pan.artem.test;

import java.security.Principal;

public class ApplicationUtil {
    public static String getPrincipalName(Principal principal) {
        if (principal != null) {
            return principal.getName();
        }
        return null;
    }
}
