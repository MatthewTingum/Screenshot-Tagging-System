-- ============================================================
--
--  Screenshot Tagger Addon (SStagger.lua)
--	Addon for WoW - SStagger Current Version 0.80
--	Kristopher Lommen, Matthew Tingum, Richard Lee
--
--	Last modified by: Kristopher Lommen
--	Last revised: February 28, 2017
--
-- ============================================================




-- ============================================================
-- Event Listener Section
-- We create a frame that acts as a listener for all events
-- that are passed to the addon by the game's various API's
-- Depending on what event our addon detects, we will execute a given section of code
-- E.g: Upon notification that the addon has loaded, we will have a proper
-- Reponse to the ADDON_LOADED event that is passed to our addon by the game
--
-- By waiting for specific events, we insure that no garbage data is read. We force it to be initialized before we try to read it. The game
-- Cannot insure that persistent variables will be initialized immediately upon startup, so we need to use events.
-- ============================================================


local timestart = format(date("%m/%d/%y %H:%M:%S")) -- Used for initial time stamp for sessions.
local frame = CreateFrame("Frame") -- Create a frame that can listen to events being sent out by the game
local addonname = "SStagger"  --Later used to filter our Addon events from other possible Addons


--Let the addon know that we want to listen for the following three events to trigger
frame:RegisterEvent("ADDON_LOADED") -- Triggers when addon is loaded successfully.
frame:RegisterEvent("PLAYER_LOGIN");-- On player logout event
frame:RegisterEvent("PLAYER_LEAVING_WORLD");-- On char logout event


--OnEvent() expects reference to the frame it is referenced from, the name of event, and name of addon are passed upon being called.
--Called whenever an event triggered 
--This function is used to determine what event it is as well as how to proceed.
local function OnEvent(self, event, name)

	if event == "PLAYER_LEAVING_WORLD" then end_session() 
		elseif  event == "PLAYER_LOGIN" then ChatFrame1:AddMessage("The player has logged in. Look at the event that triggered ->"..event) --Outputs to ingame chat box
	end
	
	if name ~= "SStagger" then return end	--Not our addon's event, so we don't want to do anything about it

	if event == "ADDON_LOADED" then
			loadSettings()			--Calls initial load functions to prepare the tables and first log file line.	
			end
end
frame:SetScript("OnEvent", OnEvent) --Redirects all events the addon detects to be parsed by above Function. MUST BE PLACED AFTER THE OnEvent FUNCTION

-- ============================================================
-- Initialization Section
-- Creates global table used for outputting log files and creates the initial login timestamp in log file
-- ============================================================
screenshotDB = screenshotDB or {} --Loads, or if not found, creates a new table for the lines of data to be outputted. 
function loadSettings() --Initial User settings are loaded in function loadSettings()

	
	local defaults = { -- Table stores the default values for user persistent variables
						X_res = 500,
						Y_res = 250,	
						ss_action = 2,
						dump_chats = 1}

	local function copyTable(source, output) --copyTable inserts all values from the source table into an output table. This is done in case no options have been set. The defaults table will overwrite the empty src table provided

				if type(output) then output = {} end -- Want to make sure that the table we're building to return starts off as empty

				-- Loop through the source table and we will place the values into the received output table
				for i, j in pairs(source) do
					--If the values are different we will overwrite the output's value with the source's value
					if type(j) ~= type(output[i]) then
						output[i] = j
					
					end
				end
			
			return output --Return output table with the defaulted values
		end
	
	local function firstTimestamp()-- Function firstTimestamp() logs the first line to the output file as a timestamp file. This is needed to seperate game sessions in the log file for future analysis.	
				ChatFrame1:AddMessage("[SStagger]:Logged session start at "..timestart)
				tinsert(screenshotDB, "SESSION START AT ".. timestart.." SAVED TO ../SavedVariables/SStagger.lua.. " ) --Logs string data into the table screenshotDB with the function tinsert()
		end
			
	UISettings = UISettings or {} -- Reuses the existing UISettings table if present. If not an empty table is made for the settings. This is stored in the LUA log file
	firstTimestamp()	      -- Calls function to place first timestamp

	if next(UISettings) == nil then --In the case where the UISettings table is empty, we are going to place default values into it.
		UISettings = copyTable(defaults, UISettings)
	end

end

function end_session() --Logs the timestamp and duration of the game session at the end of last log
	tinsert(screenshotDB, "PLAYER "..UnitName("player").." SESSION ENDED AT ".. date("%m/%d/%y %H:%M:%S")) --Logs the session end into the log file. Will allow for game sessions to be split up from the Local Application
	return nil
end



-- ============================================================
-- Hotkey Press Section (Main Behavior of the Addon)
--
-- We create a frame that acts as a listener for all events
-- that are passed to the addon by the game's various API's.
-- Depending on what event our addon detects, we will execute a given section of code.
-- I.e, Upon notification that the addon has loaded, we will have a proper
-- reponse to the ADDON_LOADED event by initializing the necessary info for the addon
--
-- By waiting for specific events, we insure that no garbage data is read. The game
-- Cannot insure that persistent variables will be initialized immediately upon startup, so we get around this with events.
-- ============================================================

function hotkey_press() --Called whenever the hotkey is pressed from within the game

	local userinfo = ""

	--Use Game's provided functions to get relevant player information for log file.
	local playerName = UnitName("player")
	local realmName =  GetRealmName()
	local ZoneText =  GetRealZoneText()
	local SubZone = GetSubZoneText ()
	local timevar = format(date()) --Time variable which will be used to create a filename format that is identical to the default naming mechanism that the game uses in the Screenshot() Function


	Screenshot()--built in function to take a SS and send it to the Wow/Screenshots directory. We need to associate our log file with this screenshot
	--Game creates screenshots with a predictable naming template WoWScrnShot_DATE_TIME.jpg
	--It exists in a specific directory distint from our output log file, so we need to make our log identical
	--As easy as possible to associate the screenshot with all the user information


	--We need to mimic the screenshot outputted by the Screenshot() function. We will build
	--The correct formatting out of the variable 'filename'
	local filename = timevar:gsub("%/", "") 
	filename = filename:gsub(":", "")
	filename = filename:gsub(" ", "_") --Substitutes blank space with underscore
	filename = "WoWScrnShot_" .. filename .. ".jpg" --It is in correct format at this point


	--variable userinfo is part of line we will be logging to the output file. 
	--User info as well as the screenshot's filename are logged correctly. This will make pairing the data with the picture easy to accomplish
	userinfo = "|" .. filename .."|" .. timevar .. "|" .. playerName .. "|" .. realmName ..  "|" .. ZoneText.. "|" .. SubZone

	getInput(userinfo, filename)--We have the basic data for the log file line. Now we need to handle the inputted data from the addon's descr and tags fields from ingame.


end



function getInput(arg, filename)--Finalizes a database entry by assembling the previously obtained user info, and now waits for the user inputted description and tags

	--The following four variables use functions I wrote for their UI properties. The lua/xml interactions are pretty verbose so the UI elements are in their own functions
	--In a future version it would be more convenient for the UI xml structures to be declared in their own .xml files, rather than being declared through the LUA script. (Like writing HTML ONLY through php, instead of using both .php and .html files)
	local f=buildFRAME() --Frame that holds in the dynamic elements(buttons/text fields/etc)
	local button = createButton()
	local descr=descrBox() --Description text field where the user enters in information
	local tags=tagsBox()   --Tags test field where the user enters in relevant tags to the event


	--Clicking the button will execute the code following it. Acts as a way to wait for the input to be entered before executing.  
	button:SetScript("OnClick", function(self) PlaySound("igMainMenuOption") self:GetParent():Hide()

	tinsert(screenshotDB,"|"..descr:GetText().."|"..tags:GetText()..arg.."|")  	--getText is a built in function used to retrieve info from text box. arg is what our userdata + filename string from the previous method

	--tinsert inserted the line into the table, which is now in our log file. It is ready to be interpreted by the User client
	--Application for our project at this stage. 
	--It is in the following format: 	|DESCR|TAGS|FILENAME.jpg|MM/DD/YY HH:MM:SS|PLAYERNAME|SERVERNAME|LOCATIONNAME|SUBLOCATIONNAME|

	ChatFrame1:AddMessage("[SStagger]:File "..filename .." saved to drive with logs.") end) --Outputs the screenshot name into the game chat box
end
-- ============================================================
-- Main UI Building (Main Behavior of the Addon)
--
-- Build all the XML Elements through LUA
-- Gross. Hide away from the other parts of the program
-- ============================================================
function buildFRAME()
	f = CreateFrame("Frame",nil,QuestLogDetailScrollChildFrame)
	f:SetBackdrop({
		  bgFile="Interface\\DialogFrame\\UI-DialogBox-Background", 
		  edgeFile="Interface\\DialogFrame\\UI-DialogBox-Border", 
		  tile=1, tileSize=32, edgeSize=32, 
		  insets={left=11, right=12, top=12, bottom=11}
	})

	f:IsResizable(true)
	f:SetScale(.85)
	f:SetWidth(UISettings["X_res"])
	f:SetHeight(UISettings["Y_res"])
	f:SetPoint("CENTER",UIParent)
	f:EnableMouse(true)
	f:SetMovable(true)
	f:RegisterForDrag("LeftButton")
	f:SetScript("OnDragStart", function(self) self:StartMoving() end)
	f:SetScript("OnDragStop", function(self) self:StopMovingOrSizing() end)
	f:SetFrameStrata("FULLSCREEN_DIALOG")
	local text = ""
	local text2=""
	f.text = f.text or f:CreateFontString(nil,"ARTWORK","QuestFont")
	f.text:SetTextColor(1,1,1,1)
	f.text:SetFont("Fonts\\FRIZQT__.TTF", 18)
	f.text:SetText(format("SS Tagger"))
	f.text:SetPoint("CENTER", 0, 100)
	f.text:SetFontObject("ChatFontNormal")

	f.text4 = f.text2 or f:CreateFontString(nil,"ARTWORK","QuestFont")
	f.text4:SetTextColor(1,1,1,1)
	f.text4:SetText(format("SStagger v 0.80\n11/22/16\nkaylo mathrax swagchard"))
	f.text4:SetPoint("BOTTOMLEFT", 15, 15)
	f.text4:SetFont("Fonts\\FRIZQT__.TTF", 8)
	f.text4:SetFontObject("ChatFontNormal")

	local buttonsettings = CreateFrame("button","MyAddonButton", f, "UIPanelButtonTemplate")
	buttonsettings:SetHeight(24)
	buttonsettings:SetWidth(70)
	buttonsettings:SetPoint("BOTTOMRIGHT", f, -25, 12)

	buttonsettings:SetText("Settings")
	buttonsettings:SetScript("OnClick", function(self) PlaySound("igMainMenuOption")  settingsmenu(f) end)


	local buttonexit = CreateFrame("button","MyAddonButton", f, "UIPanelButtonTemplate")
	buttonexit:SetHeight(30)
	buttonexit:SetWidth(30)
	buttonexit:SetPoint("TOPRIGHT", f, -2, -2)

	buttonexit:SetText(" X")
	buttonexit:SetScript("OnClick", function(self) PlaySound("igMainMenuOption") self:GetParent():Hide() ChatFrame1:AddMessage('[SStagger]:Screenshot Taken But No Links Created')  end)


	return f

end
function createButton()
	local button = CreateFrame("button","MyAddonButton", f, "UIPanelButtonTemplate")
	button:SetHeight(32)
	button:SetWidth(110)
	button:SetPoint("BOTTOM", f, "BOTTOM", 0, 12)
	button:SetText("Tag Screenshot")
	return button
end

function descrBox() --Will return a text field where the description of the screenshot is to be entered
	descr=CreateFrame("EditBox","EbT",f,"InputBoxTemplate") --Edit boxes allow for rewritable entry. Use this as the basis for getting text from the user

	descr:SetBackdrop({
		bgFile = [[Interface\Buttons\WHITE8x8]],
		edgeFile = [[Interface\Tooltips\UI-Tooltip-Border]],
		edgeSize = -5,
		insets = {left = -5, right = -5, top = -5, bottom = -5},
	})
	descr:Show()
	descr:SetBackdropColor(0, 0, 0)
	descr:SetBackdropBorderColor(0.1, 0.1, 0.1 , 1)
	descr:SetMultiLine(true)
	descr:SetSize(140,180) 
	descr:SetPoint("CENTER", -100, 0) 
	descr:SetMaxLetters(300)
	descr:SetText("Enter Description")
	descr:SetAutoFocus(false)

	descr:SetCursorPosition(0)
	descr:SetFont("Fonts\\FRIZQT__.TTF", 11)
	descr:SetJustifyH("LEFT")
	descr:SetJustifyV("BOTTOM")
	descr:SetScript("OnEditFocusGained", function(self) descr:SetText("") end) -- Resets text to empty when clicked. Will change this eventually. It's unpleasant to write an entire log and accidentally erase it all.
	return descr


end

function tagsBox() --Will return a text field where the tags are to be entered
	local tags=CreateFrame("EditBox","EbT",f,"InputBoxTemplate") 

	tags:SetBackdrop({
		bgFile = [[Interface\Buttons\WHITE8x8]],
		edgeFile = [[Interface\Tooltips\UI-Tooltip-Border]],
		edgeSize = -5,
		insets = {left = -5, right = -5, top = -5, bottom = -5},
	})

	tags:Show()
	tags:SetBackdropColor(0, 0, 0)
	tags:SetBackdropBorderColor(0.1, 0.1, 0.1, 1)

	tags:SetMultiLine(true)
	tags:SetSize(140,180) 
	tags:SetPoint("CENTER", 100, 0) 
	tags:SetMaxLetters(50)
	tags:SetAutoFocus(false)
	tags:SetCursorPosition(0)
	tags:SetFont("Fonts\\FRIZQT__.TTF", 11)
	tags:SetText("Enter Tags")
	tags:SetJustifyH("LEFT")
	tags:SetJustifyV("CENTER")
	tags:SetScript("OnEditFocusGained", function(self) tags:SetText("") end)  -- Resets text to empty when clicked. Will change this eventually. It's unpleasant to write an entire log and accidentally erase it all.

	return tags

end

-- ============================================================
-- Settings Menu (Further UI Shenanigans)
--
-- Build all the XML Elements through LUA
-- Even more gross. Drop down tables are really obnoxious to implement
-- It's the basis for the persistent variables
-- ============================================================



function settingsmenu(parentpointer)
	parentpointer:Hide()

	if not stng then
	stng=CreateFrame("Frame",parentpointer,QuestLogDetailScrollChildFrame)

	stng:SetBackdrop({
		  bgFile="Interface\\DialogFrame\\UI-DialogBox-Background", 
		  edgeFile="Interface\\DialogFrame\\UI-DialogBox-Border", 
		  tile=1, tileSize=32, edgeSize=32, 
		  insets={left=11, right=12, top=12, bottom=11}
	})

	stng:IsResizable(true)
	stng.texttitle = stng.texttitle or stng:CreateFontString(nil,"ARTWORK","QuestFont")
	stng.texttitle:SetTextColor(1,1,1,1)
	stng.texttitle:SetFont("Fonts\\FRIZQT__.TTF", 11)
	stng.texttitle:SetText(format("Settings"))
	stng.texttitle:SetPoint("CENTER",  0, 55)
	stng.texttitle:SetFontObject("ChatFontNormal")




	stng:SetScale(.85)
	stng:SetWidth(UISettings["X_res"])
	stng:SetHeight(UISettings["Y_res"])
	stng:SetPoint("CENTER", parentpointer)
	stng:EnableMouse(true)
	stng:SetMovable(true)
	stng:RegisterForDrag("LeftButton")
	stng:SetScript("OnDragStart", function(self) self:StartMoving() end)
	stng:SetScript("OnDragStop", function(self) self:StopMovingOrSizing() end)

	stng:Show()






	local dropdown1 = CreateFrame("Frame", "dropdown1", stng, "UIDropDownMenuTemplate")


	 dropdown1:ClearAllPoints()
	 dropdown1:SetPoint("CENTER", 0, 0)


	 
	  local function OnClick(self5)
	   UIDropDownMenu_SetSelectedID(dropdown1, self5:GetID())
	   ChatFrame1:AddMessage("dropdown 1 value: ".. self5:GetID())
	 end

	  local function initialize(self5, level)
	   local res_settings = {
	   "500x250",
	   "600x300",
	   "700x350",
	   "800x400",
	   "900x450",
		}
	   local info = UIDropDownMenu_CreateInfo()
	   for k,v in pairs(res_settings) do
		 info = UIDropDownMenu_CreateInfo()
		 

		 info.text = v
		 info.value = v
		 info.func = OnClick
		 UIDropDownMenu_AddButton(info, level)
	   end

	 end
	 
	  UIDropDownMenu_Initialize(dropdown1, initialize)
	  
	 UIDropDownMenu_SetWidth(dropdown1, 100);
	 UIDropDownMenu_SetButtonWidth(dropdown1, 124)
	 UIDropDownMenu_SetSelectedID(dropdown1, 1)
	 UIDropDownMenu_JustifyText(dropdown1, "LEFT")
	dropdown1:SetPoint("BOTTOMLEFT", 130, 85) 





	dropdown2 =CreateFrame("Frame", "dropdown2", stng, "UIDropDownMenuTemplate")


	dropdown2:ClearAllPoints()
	dropdown2:SetPoint("CENTER", 0, 0)

	dropdown1:Show()
	dropdown2:Show()
	 


	  local function OnClick2(self2)
	   UIDropDownMenu_SetSelectedID(dropdown2, self2:GetID())
	   ChatFrame1:AddMessage("dropdown 2 value: ".. self2:GetID())
	   UISettings["ss_action"] = self2:GetID()
	 end
	  local function initialize2(self2, level2)
	   local info2 = UIDropDownMenu_CreateInfo()

	  local action_choice = {
	   "On Tag Screenshot press",
	   "On Hotkey Press",
	 }
	   for j,l in pairs(action_choice) do
		 info2 = UIDropDownMenu_CreateInfo()
		 info2.text = l
		 info2.value = l
		 info2.func = OnClick2
		 UIDropDownMenu_AddButton(info2, level2)
	   end
	 end
	 
	 UIDropDownMenu_Initialize(dropdown2, initialize2)
	 UIDropDownMenu_SetWidth(dropdown2, 150);
	 UIDropDownMenu_SetButtonWidth(dropdown2, 124)
	 UIDropDownMenu_SetSelectedID(dropdown2, 1)
	 UIDropDownMenu_JustifyText(dropdown2, "LEFT")
	 UIDROPDOWNMENU_SHOW_TIME = .1
	dropdown2:SetPoint("BOTTOMLEFT", 130, 55) 
	end
	--after first time this happens
	 stng:Show()



	stng.text = stng.text or stng:CreateFontString(nil,"ARTWORK","QuestFont")
	stng.text:SetTextColor(1,1,1,1)
	stng.text:SetText(format("Addon Resolution:"))
	stng.text:SetPoint("BOTTOMLEFT", 30, 100)
	stng.text:SetFont("Fonts\\FRIZQT__.TTF", 8)
	stng.text:SetFontObject("ChatFontNormal")


	stng.text2 = stng.text2 or stng:CreateFontString(nil,"ARTWORK","QuestFont")
	stng.text2:SetTextColor(1,1,1,1)
	stng.text2:SetText(format("Screen Capture Behavior:"))
	stng.text2:SetPoint("BOTTOMLEFT", 30, 70)
	stng.text2:SetFont("Fonts\\FRIZQT__.TTF", 8)
	stng.text2:SetFontObject("ChatFontNormal")



	local button = CreateFrame("button","MyAddonButton", stng, "UIPanelButtonTemplate")
	button:SetHeight(32)
	button:SetWidth(110)
	button:SetPoint("BOTTOM", stng, "BOTTOM", 0, 12)
	button:SetText("Save Settings")
	button:SetScript("OnClick", function(self) PlaySound("igMainMenuOption") 
	parentpointer:Show()  
	UISettings["X_res"] = 400 + (100* ((format(UIDropDownMenu_GetSelectedID(dropdown1)))))
	UISettings["Y_res"] = 250 + (50 *     ((format(UIDropDownMenu_GetSelectedID(dropdown1)))-1   )      )

	parentpointer:SetWidth(UISettings["X_res"])
	parentpointer:SetHeight(UISettings["Y_res"])

	stng:Hide()
	end)



	local buttonexit = CreateFrame("button","MyAddonButton", stng, "UIPanelButtonTemplate")
	buttonexit:SetHeight(30)
	buttonexit:SetWidth(30)
	buttonexit:SetPoint("TOPRIGHT", stng, -2, -2)


	buttonexit:SetText(" X")
	buttonexit:SetScript("OnClick", function(self) PlaySound("igMainMenuOption") 
	parentpointer:Show() 
	stng:Hide()end)




end
