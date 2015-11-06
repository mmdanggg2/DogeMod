package mmdanggg2.doge.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import mmdanggg2.doge.Doge;
import mmdanggg2.doge.DogeInfo;
import mmdanggg2.doge.util.DogeLogger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import cpw.mods.fml.client.IModGuiFactory;
import cpw.mods.fml.client.config.ConfigGuiType;
import cpw.mods.fml.client.config.DummyConfigElement;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.GuiConfigEntries.IConfigEntry;
import cpw.mods.fml.client.config.IConfigElement;
import cpw.mods.fml.client.config.DummyConfigElement.DummyCategoryElement;
import cpw.mods.fml.client.config.DummyConfigElement.DummyListElement;
import cpw.mods.fml.client.config.GuiConfigEntries.NumberSliderEntry;

public class DogeConfigGUIFactory implements IModGuiFactory {
	
	public static class DogeConfigGUI extends GuiConfig {
	    public DogeConfigGUI(GuiScreen parent) {
	        super(parent, getConfigElements(), DogeInfo.ID, false, false, GuiConfig.getAbridgedConfigPath(Doge.config.toString()));
	    }
	    
	    private static List<IConfigElement> getConfigElements()
        {
            List<IConfigElement> list = new ArrayList<IConfigElement>();
            
            Iterator<String> catNameIterator = Doge.config.getCategoryNames().iterator();
            
            while (catNameIterator.hasNext()){
            	String catStr = catNameIterator.next();
            	DogeLogger.logDebug("Adding catagory " + catStr + " to config GUI.");
            	
            	List<IConfigElement> confEntry = new ArrayList<IConfigElement>();
            	
            	Iterator<Property> catChildIterator = Doge.config.getCategory(catStr).getOrderedValues().listIterator();
            	while (catChildIterator.hasNext()) {
            		Property childCatConf = catChildIterator.next();
            		DogeLogger.logDebug("Property in " + catStr + ": " + childCatConf.getName());
            		confEntry.add(new ConfigElement(childCatConf));
            	}
            	
            	list.add(new DummyCategoryElement(catStr, "doge.config." + catStr, confEntry));
            }
            
            return list;
        }
	}

	@Override
	public void initialize(Minecraft minecraftInstance) {}

	@Override
	public Class<? extends GuiScreen> mainConfigGuiClass() {
		return DogeConfigGUI.class;
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
		return null;
	}

	@Override
	public RuntimeOptionGuiHandler getHandlerFor(
			RuntimeOptionCategoryElement element) {
		return null;
	}

}
