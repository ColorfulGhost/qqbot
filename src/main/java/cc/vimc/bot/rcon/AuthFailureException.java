package cc.vimc.bot.rcon;

/**
 * Exception thrown by {@link RconClientException} when the specified password is incorrect.
 */
public class AuthFailureException extends RconClientException {
    public AuthFailureException() {
        super("Authentication failure");
    }
}
