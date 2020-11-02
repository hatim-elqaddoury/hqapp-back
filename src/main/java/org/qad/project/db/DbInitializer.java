package org.qad.project.db;

import org.qad.project.models.User;
import org.qad.project.models.Login;
import org.qad.project.web.UtilsController;
import org.qad.project.models.Setting;
import org.qad.project.dao.UserDao;
import org.qad.project.dao.LoginDao;
import org.qad.project.dao.SettingDao;

import java.time.Instant;

import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

@Component
@ConditionalOnProperty(name = { "app.db-init" }, matchIfMissing = false, havingValue = "true")
public class DbInitializer implements CommandLineRunner
{
    private static final Logger log = Logger.getLogger(DbInitializer.class);
    private SettingDao settingRepository;
    private LoginDao loginRepository;
    private UserDao userRepository;
    private String admEmail;
    
    public DbInitializer(final SettingDao settingRepository, final LoginDao loginRepository, final UserDao userRepository) {
        this.admEmail = "hatim-elqaddoury@outlook.fr";
        this.settingRepository = settingRepository;
        this.loginRepository = loginRepository;
        this.userRepository = userRepository;
    }
    
    public void run(final String... args) throws Exception {
        this.settingRepository.deleteAll();
        final Setting theme = new Setting("0", "theme", "dark");
        this.settingRepository.save(theme);
        DbInitializer.log.info("Database has been initialized");
        
        if (this.loginRepository.findByEmail(this.admEmail) == null) {
            this.loginRepository.saveAndFlush(new Login("hatim", "hatim.elqaddoury", this.admEmail, UtilsController.encrypt("Hatim").toString(), "Admin", null));
        }
        final Login hatim = this.loginRepository.findByEmail(this.admEmail);
        this.userRepository.saveAndFlush(new User(hatim.getId(), hatim.getUsername(), "Hatim El-Qaddoury",
        		hatim.getEmail(), hatim.getRole(), null, null, theme.getValue(), Instant.now(), null, false, "Dijon, France"));
    }
    
}