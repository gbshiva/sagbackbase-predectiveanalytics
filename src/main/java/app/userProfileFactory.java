package app;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import net.sf.ehcache.CacheManager;

public class userProfileFactory {
	public static CacheManager cmgr;
	public static Cache cache;

	public userProfileFactory(String cacheName){
		try{
		cmgr = new CacheManager();
		if (cmgr != null)
			cache = cmgr.getCache(cacheName);
			if (cache!= null)
				loadUserProfiles();
		} catch (Exception ex){
			System.out.println("Unable to connect to User Cache");
		}
		
	}

	public void loadUserProfiles() {
		try {
			userProfile usr1 = new userProfile(6212, 162, 791, 45, 4, 11, 9, 2, 12, 20, 5, 0.3049300751d, 11,
					0.8356242393d, 0, 13, 8, 7, 0.18305338d, 0.3646333889d, "M", 40, 38);
			cache.put(new Element(1, usr1));
			userProfile usr2 = new userProfile(22, 45, 55, 45, 4, 9, 9, 5, 2, 2, 5, 0.3049300751d, 11, 
					0.8356242393d, 0, 13, 8, 7, 0.18305338d, 0.3646333889d, "M", 40, 38);
			cache.put(new Element(2, usr2));
		} catch (Exception ex) {
			System.out.println("Unable to Laod userProfiles to Cache");
		}
	}
	
	public userProfile getUserProfile(int id) {
		Element e = cache.get(id);
		if ( e!= null)
			return (userProfile)e.getValue();
		else
			return new userProfile();
		
	}

}