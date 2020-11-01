package org.qad.project.assets;

import org.qad.project.models.User;

public class MailTemplate {

public static String GetMailTemplate(User usr, String CONTENT) {
	return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" + 
			"<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" + 
			"<link type=\"text/css\" id=\"dark-mode\" rel=\"stylesheet\" href>\n" + 
			"\n" + 
			"<head>\n" + 
			"    <meta charset=\"UTF-8\">\n" + 
			"    <meta content=\"width=device-width, initial-scale=1\" name=\"viewport\">\n" + 
			"    <meta name=\"x-apple-disable-message-reformatting\">\n" + 
			"    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" + 
			"    <meta content=\"telephone=no\" name=\"format-detection\">\n" + 
			"    <title></title>\n" + 
			"</head>\n" + 
			"\n" + 
			"<body>\n" + 
			"    <div class=\"es-wrapper-color\">\n" + 
			"        <table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" + 
			"            <tbody>\n" + 
			"                <tr>\n" + 
			"                    <td class=\"esd-email-paddings\" valign=\"top\">\n" + 
			"                        <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content\" align=\"center\">\n" + 
			"                            <tbody>\n" + 
			"                                <tr>\n" + 
			"                                    <td class=\"esd-stripe\" align=\"center\">\n" + 
			"                                        <table bgcolor=\"#ffffff\" class=\"es-content-body\" align=\"center\" cellpadding=\"0\"\n" + 
			"                                            cellspacing=\"0\" width=\"600\">\n" + 
			"                                            <tbody>\n" + 
			"                                                <tr>\n" + 
			"                                                    <td class=\"esd-structure\" align=\"left\">\n" + 
			"                                                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" + 
			"                                                            <tbody>\n" + 
			"                                                                <tr>\n" + 
			"                                                                    <td width=\"600\" class=\"esd-container-frame\"\n" + 
			"                                                                        align=\"center\" valign=\"top\"\n" + 
			"                                                                        esd-custom-block-id=\"78577\">\n" + 
			"                                                                        <table cellpadding=\"0\" cellspacing=\"0\"\n" + 
			"                                                                            width=\"100%\">\n" + 
			"                                                                            <tbody>\n" + 
			"                                                                                <tr>\n" + 
			"                                                                                    <td align=\"center\"\n" + 
			"                                                                                        class=\"esd-block-image\"\n" + 
			"                                                                                        style=\"font-size:0\"><a\n" + 
			"                                                                                            target=\"_blank\"><img\n" + 
			"                                                                                                class=\"adapt-img\"\n" + 
			"                                                                                                src=\"https://media-exp1.licdn.com/dms/image/C5616AQG29cysa5RKZQ/profile-displaybackgroundimage-shrink_350_1400/0?e=1609372800&v=beta&t=JqrWtw_r_OyCF547WV5Oz9bg0nkkemTr25uVHotDypE\"\n" + 
			"                                                                                                alt\n" + 
			"                                                                                                style=\"display: block;\"\n" + 
			"                                                                                                width=\"600\"></a></td>\n" + 
			"                                                                                </tr>\n" +
			"                                                                            </tbody>\n" + 
			"                                                                        </table>\n" + 
			"                                                                    </td>\n" + 
			"                                                                </tr>\n" + 
			"                                                            </tbody>\n" + 
			"                                                        </table>\n" + 
			"                                                    </td>\n" + 
			"                                                </tr>\n" + 
			"                                                <tr>\n" + 
			"                                                    <td class=\"es-p20t es-p20r es-p20l esd-structure\" align=\"left\">\n" + 
			"                                                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" + 
			"                                                            <tbody>\n" + 
			"                                                                <tr>\n" + 
			"                                                                    <td width=\"560\" class=\"esd-container-frame\"\n" + 
			"                                                                        align=\"center\" valign=\"top\">\n" + 
			"                                                                        <table cellpadding=\"0\" cellspacing=\"0\"\n" + 
			"                                                                            width=\"100%\">\n" + 
			"                                                                            <tbody>\n" + 
			"                                                                                <tr>\n" + 
			"                                                                                    <td align=\"center\"\n" + 
			"                                                                                        class=\"esd-block-text\">\n" + 
			"                                                                                        <h1>Dear "+usr.getFullname()+"</h1>\n" + 
			"                                                                                    </td>\n" + 
			"                                                                                </tr>\n" + 
			"                                                                                <tr>\n" + 
			"                                                                                    <td align=\"center\"\n" + 
			"                                                                                        class=\"esd-block-spacer es-p20\"\n" + 
			"                                                                                        style=\"font-size:0\">\n" + 
			"                                                                                        <table border=\"0\" width=\"10%\"\n" + 
			"                                                                                            height=\"100%\"\n" + 
			"                                                                                            cellpadding=\"0\"\n" + 
			"                                                                                            cellspacing=\"0\"\n" + 
			"                                                                                            style=\"display: inline-table; width: 10% !important;\">\n" + 
			"                                                                                            <tbody>\n" + 
			"                                                                                                <tr>\n" + 
			"                                                                                                    <td\n" + 
			"                                                                                                        style=\"border-bottom: 5px solid #012d60; background: none; height: 1px; width: 100%; margin: 0px;\">\n" + 
			"                                                                                                    </td>\n" + 
			"                                                                                                </tr>\n" + 
			"                                                                                            </tbody>\n" + 
			"                                                                                        </table>\n" + 
			"                                                                                    </td>\n" + 
			"                                                                                </tr>\n" + 
			"                                                                            </tbody>\n" + 
			"                                                                        </table>\n" + 
			"                                                                    </td>\n" + 
			"                                                                </tr>\n" + 
			"                                                            </tbody>\n" + 
			"                                                        </table>\n" + 
			"                                                    </td>\n" + 
			"                                                </tr>\n" + 
			"                                                <tr>\n" + 
			"                                                    <td class=\"esd-structure es-p20t es-p20b es-p35r es-p35l\"\n" + 
			"                                                        style=\"background-position: center top;\" align=\"left\"\n" + 
			"                                                        esd-custom-block-id=\"78583\">\n" + 
			"                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" + 
			"                                                            <tbody>\n" + 
			"                                                                <tr>\n" + 
			"                                                                    <td class=\"esd-container-frame\" width=\"530\"\n" + 
			"                                                                        valign=\"top\" align=\"center\">\n" + 
			"                                                                        <table style=\"background-color: transparent;\"\n" + 
			"                                                                            width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"\n" + 
			"                                                                            bgcolor=\"transparent\">\n" + 
			"                                                                            <tbody>\n" + 
			"                                                                                <tr>\n" + 
			"                                                                                    <td style=\"font-size: 18px;\" "+ 
			"                                                                                        align=\"center\">\n" + 
			"                                                                                        "+ CONTENT +
			"                                                                                    </td>\n" + 
			"                                                                                </tr>\n" + 
			"                                                                                <tr>\n" + 
			"                                                                                    <td align=\"center\" style=\"font-size:0;\"><a\n" + 
			"                                                                                            target=\"_blank\"><img style=\"border-radius: 50%;\"\n" + 
			"                                                                                                src=\"https://media-exp1.licdn.com/dms/image/C5603AQGgrT2B_Zm2GQ/profile-displayphoto-shrink_400_400/0?e=1609372800&v=beta&t=9dlHvuPk8o2VOgRr1zs9sPx_ti0nutqGTkNRJNc3Nrs\"\n" + 
			"                                                                                                alt=\"Hatim El-Qaddoury\"\n" + 
			"                                                                                                title=\"Hatim El-Qaddoury\\\"\n" + 
			"                                                                                                width=\"80\"></a></td>\n" + 
			"                                                                                </tr>\n" + 
			"                                                                                <tr>\n" + 
			"                                                                                    <td class=\"esd-block-text es-p15r es-p15l\"\n" + 
			"                                                                                        align=\"center\">\n" + 
			"                                                                                        <p><strong>Hatim\n" + 
			"                                                                                                El-QADDOURY\n" + 
			"                                                                                        </strong></p>\n" + 
			"                                                                                        <p style=\"color: #666666;\">\n" + 
			"                                                                                            CEO\n" + 
			"                                                                                        </p>\n" + 
			"                                                                                    </td>\n" + 
			"                                                                                </tr>\n" + 
			"                                                                            </tbody>\n" + 
			"                                                                        </table>\n" + 
			"                                                                    </td>\n" + 
			"                                                                </tr>\n" + 
			"                                                            </tbody>\n" + 
			"                                                        </table>\n" + 
			"                                                    </td>\n" + 
			"                                                </tr>\n" + 
			"                                            </tbody>\n" + 
			"                                        </table>\n" + 
			"                                    </td>\n" + 
			"                                </tr>\n" + 
			"                            </tbody>\n" + 
			"                        </table>\n" + 
			"                        <table class=\"es-footer\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n" + 
			"                            <tbody>\n" + 
			"                                <tr>\n" + 
			"                                    <td class=\"esd-stripe\" align=\"center\" esd-custom-block-id=\"78584\">\n" + 
			"                                        <table class=\"es-footer-body\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\"\n" + 
			"                                            bgcolor=\"#ffffff\" align=\"center\">\n" + 
			"                                            <tbody>\n" + 
			"                                                <tr>\n" + 
			"                                                    <td class=\"esd-structure es-p20t es-p20b es-p20r es-p20l\"\n" + 
			"                                                        style=\"background-color: #040101;\" align=\"left\"\n" + 
			"                                                        esd-custom-block-id=\"55705\" bgcolor=\"#040101\">\n" + 
			"                                                        <table cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\n" + 
			"                                                            <tbody>\n" + 
			"                                                                <tr>\n" + 
			"                                                                    <td class=\"esd-container-frame\" width=\"560\"\n" + 
			"                                                                        align=\"left\">\n" + 
			"                                                                        <table style=\"background-position: center top;\"\n" + 
			"                                                                            width=\"100%\" cellspacing=\"0\"\n" + 
			"                                                                            cellpadding=\"0\">\n" + 
			"                                                                            <tbody>\n" + 
			"                                                                                <tr>\n" + 
			"                                                                                    <td align=\"center\"\n" + 
			"                                                                                        class=\"esd-block-social\"\n" + 
			"                                                                                        style=\"font-size:0\">\n" + 
			"                                                                                        <table cellpadding=\"0\"\n" + 
			"                                                                                            cellspacing=\"0\"\n" + 
			"                                                                                            class=\"es-table-not-adapt es-social\">\n" + 
			"                                                                                            <tbody>\n" + 
			"                                                                                                <tr>\n" + 
			"                                                                                                    <td align=\"center\"\n" + 
			"                                                                                                        valign=\"top\"\n" + 
			"                                                                                                        esd-tmp-icon-type=\"linkedin\">\n" + 
			"                                                                                                        <a target=\"_blank\" href=\"https://www.linkedin.com/in/hatim-el-qaddoury/\"> \n" + 
			"                                                                                                            <img src=\"https://stripo.email/static/assets/img/social-icons/logo-white/linkedin-logo-white.png\"\n" + 
			"                                                                                                                alt=\"Li\"\n" + 
			"                                                                                                                title=\"linkedin\"\n" + 
			"                                                                                                                width=\"32\"></a>\n" + 
			"                                                                                                    </td>\n" + 
			"                                                                                                    <td align=\"center\"\n" + 
			"                                                                                                        valign=\"top\"\n" + 
			"                                                                                                        esd-tmp-icon-type=\"instagram\"\n" + 
			"                                                                                                        class=\"es-p10r\">\n" + 
			"                                                                                                        <a target=\"_blank\" href=\"https://www.instagram.com/hatim.elqaddoury/\">\n" + 
			"                                                                                                            <img src=\"https://tlr.stripocdn.email/content/assets/img/social-icons/logo-white/instagram-logo-white.png\"\n" + 
			"                                                                                                                alt=\"Ig\"\n" + 
			"                                                                                                                title=\"Instagram\"\n" + 
			"                                                                                                                width=\"32\"></a>\n" + 
			"                                                                                                    </td>\n" + 
			"                                                                                                    <td align=\"center\"\n" + 
			"                                                                                                        valign=\"top\"\n" + 
			"                                                                                                        esd-tmp-icon-type=\"facebook\"\n" + 
			"                                                                                                        class=\"es-p10r\">\n" + 
			"                                                                                                        <a target=\"_blank\" href=\"https://www.facebook.com/hatim.elqaddoury/\">\n" + 
			"                                                                                                            <img src=\"https://tlr.stripocdn.email/content/assets/img/social-icons/logo-white/facebook-logo-white.png\"\n" + 
			"                                                                                                                alt=\"Fb\"\n" + 
			"                                                                                                                title=\"Facebook\"\n" + 
			"                                                                                                                width=\"32\"></a>\n" + 
			"                                                                                                    </td>\n" + 
			"                                                                                                    <td align=\"center\"\n" + 
			"                                                                                                        valign=\"top\"\n" + 
			"                                                                                                        esd-tmp-icon-type=\"twitter\"\n" + 
			"                                                                                                        class=\"es-p10r\">\n" + 
			"                                                                                                        <a target=\"_blank\" href=\"https://twitter.com/HatimElQaddoury\">\n" + 
			"                                                                                                            <img src=\"https://tlr.stripocdn.email/content/assets/img/social-icons/logo-white/twitter-logo-white.png\"\n" + 
			"                                                                                                                alt=\"Tw\"\n" + 
			"                                                                                                                title=\"Twitter\"\n" + 
			"                                                                                                                width=\"32\"></a>\n" + 
			"                                                                                                    </td>\n" + 
			"                                                                                                </tr>\n" + 
			"                                                                                            </tbody>\n" + 
			"                                                                                        </table>\n" + 
			"                                                                                    </td>\n" + 
			"                                                                                </tr>\n" + 
			"                                                                            </tbody>\n" + 
			"                                                                        </table>\n" + 
			"                                                                    </td>\n" + 
			"                                                                </tr>\n" + 
			"                                                            </tbody>\n" + 
			"                                                        </table>\n" + 
			"                                                    </td>\n" + 
			"                                                </tr>\n" + 
			"                                                <tr>\n" + 
			"                                                    <td class=\"esd-structure es-p20t es-p20b es-p20r es-p20l\"\n" + 
			"                                                        align=\"left\">\n" + 
			"                                                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" + 
			"                                                            <tbody>\n" + 
			"                                                                <tr>\n" + 
			"                                                                    <td width=\"560\" class=\"esd-container-frame\"\n" + 
			"                                                                        align=\"center\" valign=\"top\">\n" + 
			"                                                                        <table cellpadding=\"0\" cellspacing=\"0\"\n" + 
			"                                                                            width=\"100%\">\n" + 
			"                                                                            <tbody>\n" + 
			"                                                                                <tr>\n" + 
			"                                                                                    <td align=\"center\"\n" +
			"                                                                                        <p style=\"font-size: 10px;\"> " +
			"                                                                                            Thank you for using HQApp\n" + 
			"                                                                                            typesetting industry. Lorem\n" + 
			"                                                                                            Ipsum has been the\n" + 
			"                                                                                            industry's standard dummy\n" + 
			"                                                                                            text ever since the 1500s,\n" + 
			"                                                                                            when an unknown printer took\n" + 
			"                                                                                            a galley of type and\n" + 
			"                                                                                            scrambled it to make a type\n" + 
			"                                                                                            specimen book.</p>\n" + 
			"                                                                                    </td>\n" + 
			"                                                                                </tr>\n" + 
			"                                                                                <tr>\n" + 
			"                                                                                    <td align=\"center\"\n" + 
			"                                                                                        class=\"esd-block-text es-p20t\">\n" + 
			"                                                                                        <p> HQApp Team</p>\n" + 
			"                                                                                    </td>\n" + 
			"                                                                                </tr>\n" +
			"																				 <tr>\n" + 
			"                                                                                    <td class=\"esd-block-image es-infoblock made_with\"\n" + 
			"                                                                                        align=\"center\"\n" + 
			"                                                                                        style=\"font-size:0\"><a\n" + 
			"                                                                                            target=\"_blank\"\n" + 
			"                                                                                            href=\"http://192.168.1.29:4200/\"><img\n" + 
			"                                                                                                src=\"https://www.drupal.org/files/styles/grid-3/public/ClubHQ-Beta-Logo.png?itok=0BLytlmu\"\n" + 
			"                                                                                                alt width=\"125\"></a>\n" + 
			"                                                                                    </td>\n" + 
			"                                                                                </tr>\n" + 
			"                                                                            </tbody>\n" + 
			"                                                                        </table>\n" + 
			"                                                                    </td>\n" + 
			"                                                                </tr>\n" + 
			"                                                            </tbody>\n" + 
			"                                                        </table>\n" + 
			"                                                    </td>\n" + 
			"                                                </tr>\n" + 
			"                                            </tbody>\n" + 
			"                                        </table>\n" + 
			"                                    </td>\n" + 
			"                                </tr>\n" + 
			"                            </tbody>\n" + 
			"                        </table>\n" + 
			"                    </td>\n" + 
			"                </tr>\n" + 
			"            </tbody>\n" + 
			"        </table>\n" + 
			"    </div>\n" + 
			"</body>\n" + 
			"\n" + 
			"</html>";
}

}