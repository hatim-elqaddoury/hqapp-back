package org.qad.project.db;

import org.qad.project.dao.SettingDao;
import org.qad.project.models.Setting;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import org.springframework.stereotype.Component;
@Component
@ConditionalOnProperty(name = "app.db-init", matchIfMissing = false, havingValue = "true")
public class DbInitializer implements CommandLineRunner {
	
	private SettingDao settingRepository;
	

    public DbInitializer(SettingDao settingRepository){
        this.settingRepository = settingRepository;
    }

	@Override
	public void run(String... args) throws Exception {
		this.settingRepository.deleteAll();
		
		Setting theme = new Setting("0", "theme", "dark");
		this.settingRepository.save(theme);
		
        System.out.println(" -- Database has been initialized");
		
	}

}
