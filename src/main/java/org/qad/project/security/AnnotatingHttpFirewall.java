package org.qad.project.security;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.firewall.FirewalledRequest;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.security.web.firewall.StrictHttpFirewall;

public class AnnotatingHttpFirewall extends StrictHttpFirewall {
	public static final String HTTP_HEADER_REQUEST_REJECTED_FLAG = "X-HttpFirewall-RequestRejectedFlag";
	public static final String HTTP_HEADER_REQUEST_REJECTED_REASON = "X-HttpFirewall-RequestRejectedReason";
	private static final Logger LOGGER = Logger.getLogger(AnnotatingHttpFirewall.class.getName());

	public FirewalledRequest getFirewalledRequest(final HttpServletRequest request) {
      try {
         this.inspect(request);
         return super.getFirewalledRequest(request);
      } catch (RequestRejectedException e) {
         String requestUrl = request.getRequestURL().toString();
         if (!requestUrl.contains(";jsessionid=")) {
            if (LOGGER.isLoggable(Level.WARNING)) {
               LOGGER.log(Level.WARNING, "Intercepted RequestBlockedException: Remote Host: " + request.getRemoteHost() + " User Agent: " + request.getHeader("User-Agent") + " Request URL: " + request.getRequestURL().toString());
            }

            request.setAttribute("X-HttpFirewall-RequestRejectedFlag", Boolean.TRUE);
            request.setAttribute("X-HttpFirewall-RequestRejectedReason", e.getMessage());
         }

         // Suppress the RequestBlockedException and pass the request through
         // with the additional attribute.
         return new FirewalledRequest(request)
         {
             @Override
             public void reset()
             {
                 return;
             }
         };
      }
   }

	public HttpServletResponse getFirewalledResponse(final HttpServletResponse response) {
		return super.getFirewalledResponse(response);
	}

	public void inspect(final HttpServletRequest request) throws RequestRejectedException {
		String requestUri = request.getRequestURI();
		if (requestUri.endsWith("/wp-login.php")) {
			throw new RequestRejectedException("The request was rejected because it is a vulnerability scan.");
		} else if (requestUri.endsWith(".php")) {
			throw new RequestRejectedException("The request was rejected because it is a likely vulnerability scan.");
		}
	}
}