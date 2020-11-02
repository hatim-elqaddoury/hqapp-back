package org.qad.project.assets;


public class MailHtmlResponse {

	public static String GetMailHtmlResponse(String link_picture, String CONTENT) {
		
		
		return "<table style=\"background-color: transparent;\"\n" + 
				"                   width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"\n" + 
				"                   bgcolor=\"transparent\">\n" + 
				"                   <tbody>\n" + 
				"                   <tr>\n" + 
				"                   <td style=\"font-size: 19px;\" "+ 
				"                   align=\"center\">\n" + 
				"                   <br/> "+CONTENT+" <br/><br/>"+
				"                   </td>\n" + 
				"                   </tr>\n" + 
				"                   <tr>\n" + 
				"                   <td align=\"center\" style=\"font-size:10;\"><a\n" + 
				"                   target=\"_blank\"><img style=\"border-radius: 50%;\"\n" + 
				"                   src=\" "+link_picture+"  \"\n" + 
				"                   alt=\"Hatim El-Qaddoury\"\n" + 
				"                   title=\"Hatim El-Qaddoury\\\"\n" + 
				"                   width=\"90\"></a></td>\n" + 
				"                   </tr>\n" + 
				"                   <tr>\n" + 
				"                   <td class=\"esd-block-text es-p15r es-p15l\"\n" + 
				"                   align=\"center\">\n" +  
				"                   <p style=\"color: #666666;\">\n" + 
				"                   <br/>HQApp team\n" + 
				"                   </p>\n" + 
				"                   </td>\n" + 
				"                   </tr>\n" + 
				"                   </tbody>\n" + 
				"                   </table>\n";
	}
	
}