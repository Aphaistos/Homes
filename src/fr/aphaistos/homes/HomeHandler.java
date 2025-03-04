package fr.aphaistos.homes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

import fr.aphaistos.homes.json.Home;
import fr.aphaistos.homes.json.HomeOwner;

public class HomeHandler {
	private Logger logger;
	private File file;
	private final Gson gson;
	private ArrayList<HomeOwner> homes_data = new ArrayList<HomeOwner>();
	
	public HomeHandler(Logger logger, String filepath) {
		this.file = new File(filepath);
		this.logger = logger;
		this.gson = new GsonBuilder().setPrettyPrinting().create();
	}
	
	@SuppressWarnings("unchecked")
	public void readData() {
		this.createFile();
		try {
			String data_str = new String(Files.readAllBytes(Paths.get(getFilepath())), StandardCharsets.UTF_8);
			ArrayList<LinkedTreeMap<String, Object>> data = this.gson.fromJson(data_str, this.homes_data.getClass());
			if (data != null)
				for (LinkedTreeMap<String, Object> owner_data : data)
					homes_data.add(HomeOwner.fromLinkedTreeMap(owner_data));
		} catch (IOException e) {
			logger.warning("IO error while reading `" + getFilepath() + "` file.");
		}
	}
	
	public void addOwner(UUID uuid) {
		addOwner(uuid.toString());
	}
	public void addOwner(String uuid) {
		addOwner(new HomeOwner(uuid, new ArrayList<Home>()));
	}
	public void addOwner(HomeOwner owner) {
		if (this.getOwnerByUUID(owner.getUuid()) != null)
			return;
		
		this.homes_data.add(owner);
		writeData();
	}
	
	public void setHome(UUID uuid, Home home) {
		setHome(uuid.toString(), home);
	}
	public void setHome(String uuid, Home home) {
		HomeOwner owner = this.getOwnerByUUID(uuid);
		ArrayList<Home> homes;
		if (owner == null) {
			homes = new ArrayList<Home>();
			homes.add(home);
			owner = new HomeOwner(uuid.toString(), homes);
			this.homes_data.add(owner);
		} else {
			homes = owner.getHomes();
			int id = getHomeIdByName(homes, home.getName());
			if (id == -1)
				homes.add(home);
			else
				homes.set(id, home);
			this.homes_data.set(getOwnerListId(uuid), owner);
		}
		writeData();
	}
	
	public Home getHome(UUID uuid, String name) {
		return getHome(uuid.toString(), name);
	}
	public Home getHome(String uuid, String name) {
		HomeOwner owner = this.getOwnerByUUID(uuid);
		if (owner != null) {
			ArrayList<Home> homes = owner.getHomes();
			
			for (Home h : homes)
				if (h.getName().equals(name))
					return h;
		}
		return null;
	}
	public ArrayList<Home> getHomes(UUID uuid) {
		return getHomes(uuid.toString());
	}
	public ArrayList<Home> getHomes(String uuid) {
		HomeOwner owner = this.getOwnerByUUID(uuid);
		ArrayList<Home> homes = new ArrayList<Home>();
		if (owner != null) {
			homes = owner.getHomes();
		} else {
			owner = new HomeOwner(uuid.toString(), homes);
			this.homes_data.add(owner);
			writeData();
		}
		return homes;
	}

	public ArrayList<HomeOwner> getData() {
		return this.homes_data;
	}
	public String getFilepath() {
		return this.file.getAbsolutePath();
	}
	
	private void createFile() {
		if (!this.file.exists()) {
			try {
				this.file.createNewFile();
			} catch (IOException e) {
				logger.warning("IO error while creating `" + getFilepath() + "` file.");
			}
		}
	}
	public HomeOwner getOwnerByUUID(UUID uuid) {
		return getOwnerByUUID(uuid.toString());
	}
	public HomeOwner getOwnerByUUID(String uuid) {
		for (HomeOwner owner : this.homes_data)
			if (owner.getUuid().equals(uuid))
				return owner;
		return null;
	}
	public int getOwnerListId(UUID uuid) {
		return getOwnerListId(uuid.toString());
	}
	public int getOwnerListId(String uuid) {
		for(int i = 0; i < this.homes_data.size(); i++)
			if (this.homes_data.get(i).getUuid().equals(uuid))
				return i;
		return -1;
	}
	public static int getHomeIdByName(ArrayList<Home> homes, String name) { // Can be used as a 'contains fonction' too
		for (int i = 0; i < homes.size(); i++)
			if (homes.get(i).getName().equals(name))
				return i;
		
		return -1;
	}
	
	private void writeData() {
		String json = this.gson.toJson(this.homes_data);
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			logger.warning("IO error while writing to `" + getFilepath() + "` file.");
		}
	}
}