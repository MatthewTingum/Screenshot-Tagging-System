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
local chateventframe = CreateFrame("Frame") -- Create a frame that can listen to events being sent out by the game
local addonname = "SStagger"  --Later used to filter our Addon events from other possible Addons

chatlog = {}

chatevents = {
"CHAT_MSG_CHANNEL",
"CHAT_MSG_WHISPER",
"CHAT_MSG_CHANNEL_NOTICE" 
,"CHAT_MSG_BATTLEGROUND"
        ,"CHAT_MSG_BATTLEGROUND_LEADER" 
        ,"CHAT_MSG_WHISPER" 
        ,"CHAT_MSG_WHISPER_INFORM" 
        ,"CHAT_MSG_MONSTER_WHISPER" 
        ,"CHAT_MSG_RAID" 
        ,"CHAT_MSG_RAID_LEADER" 
        ,"CHAT_MSG_RAID_WARNING" 
        ,"CHAT_MSG_PARTY" 
        ,"CHAT_MSG_PARTY_LEADER" 
        ,"CHAT_MSG_SAY" 
        ,"CHAT_MSG_MONSTER_SAY" 
        ,"CHAT_MSG_YELL" 
        ,"CHAT_MSG_MONSTER_YELL" 
        ,"CHAT_MSG_OFFICER" 
        ,"CHAT_MSG_GUILD" 
        ,"CHAT_MSG_EMOTE" 
        ,"CHAT_MSG_MONSTER_EMOTE" 
        ,"CHAT_MSG_BN_WHISPER" 
        ,"CHAT_MSG_BN_WHISPER_INFORM" 
        ,"CHAT_MSG_ACHIEVEMENT" 
        ,"CHAT_MSG_GUILD_ACHIEVEMENT" 
        ,"CHAT_MSG_INSTANCE_CHAT" ,
"CHAT_MSG_INSTANCE_CHAT_LEADER"



}

--Let the addon know that we want to listen for the following three events to trigger
frame:RegisterEvent("ADDON_LOADED") -- Triggers when addon is loaded successfully.
frame:RegisterEvent("PLAYER_LOGIN")-- On player logout event
frame:RegisterEvent("PLAYER_LEAVING_WORLD")-- On char logout event

    for event,v in pairs(chatevents) do
	--ChatFrame1:AddMessage(v)
        frame:RegisterEvent(v)
    end
	
--OnEvent() expects reference to the frame it is referenced from, the name of event, and name of addon are passed upon being called.
--Called whenever an event triggered 
--This function is used to determine what event it is as well as how to proceed.
local function OnEvent(self, event, ...)
		for list,v in pairs(chatevents) do
			
			if event == v then
				--ChatFrame1:AddMessage(v)
				HandleMessage(event,...)
			end
				
				
				
		end
	if event == "PLAYER_LEAVING_WORLD" then end_session() 

	
	if ... ~= "SStagger" then return end	--Not our addon's event, so we don't want to do anything about it

	if event == "ADDON_LOADED" then
			loadSettings()			--Calls initial load functions to prepare the tables and first log file line.	
			end
	
end
end
frame:SetScript("OnEvent", OnEvent) --Redirects all events the addon detects to be parsed by above Function. MUST BE PLACED AFTER THE OnEvent FUNCTION

-- ============================================================
-- Initialization Section
-- Creates global table used for outputting log files and creates the initial login timestamp in log file
-- ============================================================
screenshotDB = screenshotDB or {} --Loads, or if not found, creates a new table for the lines of data to be outputted. 
UISettings = UISettings or {}

function loadSettings() --Initial User settings are loaded in function loadSettings()

	
	local defaults = { -- Table stores the default values for user persistent variables
						X_res = 500,
						Y_res = 250,	
						dump_chats = 1,
						chat_lines = 25}

	local function copyTable(source, output) --copyTable inserts all values from the source table into an output table. This is done in case no options have been set. The defaults table will overwrite the empty src table provided

				if type(output) then output = {} end -- Want to make sure that the table we're building to return starts off as empty

				-- Loop through the source table and we will place the values into the received output table
				for i, j in pairs(source) do
					output[i] = j	
					--tinsert(screenshotDB,i .. " = " .. j)
					
				end
			
			return output --Return output table with the defaulted values
		end
	
	local function firstTimestamp()-- Function firstTimestamp() logs the first line to the output file as a timestamp file. This is needed to seperate game sessions in the log file for future analysis.	
				--ChatFrame1:AddMessage("[SStagger]:Logged session start at "..timestart)
				--tinsert(screenshotDB, "/!!/PLAYER "..UnitName("player").." SESSION START AT ".. timestart.."/!!/") --Logs string data into the table screenshotDB with the function tinsert()
	end
		
		
	 -- Reuses the existing UISettings table if present. If not an empty table is made for the settings. This is stored in the LUA log file
	--firstTimestamp()	      -- Calls function to place first timestamp
	UISettings = UISettings or {}
	if next(UISettings) == nil then --In the case where the UISettings table is empty, we are going to place default values into it.
		UISettings = copyTable(defaults, UISettings)
		--UISettings = defaults
	end

end

function end_session() --Logs the timestamp and duration of the game session at the end of last log
	--tinsert(screenshotDB, "/??/PLAYER "..UnitName("player").." SESSION ENDED AT ".. date("%m/%d/%y %H:%M:%S").."/??/") --Logs the session end into the log file. Will allow for game sessions to be split up from the Local Application
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
	loadSettings()
	local userinfo = ""

	--Use Game's provided functions to get relevant player information for log file.
	local playerName = UnitName("player")
	local realmName =  GetRealmName()
	local ZoneText =  GetRealZoneText()
	local SubZone = GetSubZoneText ()
	


	


	--We need to mimic the screenshot outputted by the Screenshot() function. We will build
	--The correct formatting out of the variable 'filename'
	
	if (f) then
		--ChatFrame1:AddMessage("already exists")
		f = nil
		end
	
	

		--The following four variables use functions I wrote for their UI properties. The lua/xml interactions are pretty verbose so the UI elements are in their own functions
	--In a future version it would be more convenient for the UI xml structures to be declared in their own .xml files, rather than being declared through the LUA script. (Like writing HTML ONLY through php, instead of using both .php and .html files)
	local f=buildFRAME() --Frame that holds in the dynamic elements(buttons/text fields/etc)
	local button = createButton()
	local descr=descrBox() --Description text field where the user enters in information
	local tags=tagsBox()   --Tags test field where the user enters in relevant tags to the event

	

	--Clicking the button will execute the code following it. Acts as a way to wait for the input to be entered before executing.  
	button:SetScript("OnClick", function(self) PlaySound("igMainMenuOption") self:GetParent():Hide()
     local timevar = format(date()) --Time variable which will be used to create a filename format that is identical to the default naming mechanism that the game uses in the Screenshot() Function
	 local filename = timevar:gsub("%/", "") 
	filename = filename:gsub(":", "")
	filename = filename:gsub(" ", "_") --Substitutes blank space with underscore
	filename = "WoWScrnShot_" .. filename .. ".jpg" --It is in correct format at this point


	--variable userinfo is part of line we will be logging to the output file. 
	--User info as well as the screenshot's filename are logged correctly. This will make pairing the data with the picture easy to accomplish
	userinfo = "|" .. filename .."|" .. timevar .. "|" .. playerName .. "|" .. realmName ..  "|" .. ZoneText.. "|" .. SubZone
	
	 
	 
	 
	 
	tinsert(screenshotDB,"|"..descr:GetText().."|"..tags:GetText()..userinfo.."|" .. getChatLog() .. " |")  	--getText is a built in function used to retrieve info from text bo--x. arg is what our userdata + filename string from the previous method
	--tinsert inserted the line into the table, which is now in our log file. It is ready to be interpreted by the User client
	--Application for our project at this stage. 
	--It is in the following format: 	|DESCR|TAGS|FILENAME.jpg|MM/DD/YY HH:MM:SS|PLAYERNAME|SERVERNAME|LOCATIONNAME|SUBLOCATIONNAME|

	
	
	Screenshot()--built in function to take a SS and send it to the Wow/Screenshots directory. We need to associate our log file with this screenshot
	--Game creates screenshots with a predictable naming template WoWScrnShot_DATE_TIME.jpg
	--It exists in a specific directory distint from our output log file, so we need to make our log identical
	--As easy as possible to associate the screenshot with all the user information
	
	
	ChatFrame1:AddMessage("[SStagger]:File "..filename .." saved to drive with logs.") wipeChatLog() end) --Outputs the screenshot name into the game chat box
	
	
	
	
	

end




function getInput(arg, filename)--Finalizes a database entry by assembling the previously obtained user info, and now waits for the user inputted description and tags


	
end
-- ============================================================
-- Chat Log Related Aspects
--
-- ============================================================
function wipeChatLog()

	for i,j in pairs(chatlog) do
		chatlog [i] = nil
	end
end


function getChatLog()
	result = ""
	for i,j in pairs(chatlog) do

		if j ~= "" then
		--ChatFrame1:AddMessage(j)
		result = result ..j.. "\n"
		end
	end
	--ChatFrame1:AddMessage(result)
	return result
end
 local pop = function (t)
    local key, value = next(t)
    if key ~= nil then
        t[key] = nil
    end
    return key, value
end

function AddNewMessage(msg)


--checks to see if table is full already. then deletes last entry and adds to beginning

--local key, value = pop(chatlog)
--tremove(chatlog, [UISettings["chat_lines"])
if (chatlog[UISettings["chat_lines"]]) then
	tremove(chatlog,1)
	tinsert(chatlog,UISettings["chat_lines"], msg)
else
tinsert(chatlog, msg)
end



--ChatFrame1:AddMessage("Popped".. value);
--ChatFrame1:AddMessage("Yeah");


end











function HandleMessage(event, ...)

 
  -- Getting info from event args
  local message, sender, _, _, _, flags, _, _, channelName, _, _, guid = ...
--ChatFrame1:AddMessage(message)
  --ChatFrame1:AddMessage(sender)

     -- Finally, capture the message if it is not nil
    if message ~= nil then
	  
      AddNewMessage(format(date("%m/%d/%y %H:%M:%S")) .. ":["..sender .."]: " .. message)


	end


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
	f.text4:SetText(format("SStagger v 0.80\n04/22/17\n"))
	f.text4:SetPoint("BOTTOMLEFT", 15, 15)
	f.text4:SetFont("Fonts\\FRIZQT__.TTF", 8)
	f.text4:SetFontObject("ChatFontNormal")

	local buttonsettings = CreateFrame("button","MyAddonButton", f, "UIPanelButtonTemplate")
	buttonsettings:SetHeight(24)
	buttonsettings:SetWidth(70)
	buttonsettings:SetPoint("BOTTOMRIGHT", f, -25, 12)

	buttonsettings:SetText("Settings")
	buttonsettings:SetScript("OnClick", function(self) PlaySound("igMainMenuOption") self:GetParent():Hide()  settingsmenu(f) end)


	local buttonexit = CreateFrame("button","MyAddonButton", f, "UIPanelButtonTemplate")
	buttonexit:SetHeight(30)
	buttonexit:SetWidth(30)
	buttonexit:SetPoint("TOPRIGHT", f, -2, -2)

	buttonexit:SetText(" X")
	buttonexit:SetScript("OnClick", function(self) PlaySound("igMainMenuOption") self:GetParent():Hide()   end)


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
	--ChatFrame1:AddMessage("SETTING MENU DOESNT exists")
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

	--stng:Show()






	local dropdown1 = CreateFrame("Frame", "dropdown1", stng, "UIDropDownMenuTemplate")


	 dropdown1:ClearAllPoints()
	 dropdown1:SetPoint("CENTER", 0, 0)


	 
	  local function OnClick(self5)
	   UIDropDownMenu_SetSelectedID(dropdown1, self5:GetID())
	   --ChatFrame1:AddMessage("dropdown 1 value: ".. self5:GetID())
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
	 
	 
	 
	 UIDropDownMenu_SetSelectedID(dropdown1, ((UISettings["X_res"] - 400 ) / 100 ))
	 UIDropDownMenu_JustifyText(dropdown1, "LEFT")
	dropdown1:SetPoint("BOTTOMLEFT", 130, 85) 





	dropdown2 =CreateFrame("Frame", "dropdown2", stng, "UIDropDownMenuTemplate")


	dropdown2:ClearAllPoints()
	dropdown2:SetPoint("CENTER", 0, 0)

	dropdown1:Show()
	dropdown2:Show()
	 


	  local function OnClick2(self2)
	   UIDropDownMenu_SetSelectedID(dropdown2, self2:GetID())
	   --ChatFrame1:AddMessage("dropdown 2 value: ".. self2:GetID())
	   UISettings["chat_lines"] = (self2:GetID() -1) * 25
	 end
	  local function initialize2(self2, level2)
	   local info2 = UIDropDownMenu_CreateInfo()

	  local action_choice = {
	    "Disable Chatlog Dump",
	   "Last 25 lines",
	   "Last 50 lines",
	   "Last 75 lines",
	   "Last 100 lines"
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
	 UIDropDownMenu_SetSelectedID(dropdown2,1+(UISettings["chat_lines"]  /25) )
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
	stng.text2:SetText(format("Chatlog Dump Settings:"))
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
	stng:SetWidth(UISettings["X_res"])
	stng:SetHeight(UISettings["Y_res"])
	
	stng:Hide()
	
	end)



	local buttonexit = CreateFrame("button","MyAddonButton", stng, "UIPanelButtonTemplate")
	buttonexit:SetHeight(30)
	buttonexit:SetWidth(30)
	buttonexit:SetPoint("TOPRIGHT", stng, -2, -2)


	buttonexit:SetText(" X")
	buttonexit:SetScript("OnClick", function(self) PlaySound("igMainMenuOption") 
	parentpointer:Show() 
	stng:Hide()
	
	end)




end
