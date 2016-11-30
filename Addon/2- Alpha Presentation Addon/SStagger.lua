screenshotDB = screenshotDB or {} --Loads, or if not found, creates a db for outputting data logs.
local settings = {} -- Placeholder table for user settings that will eventually be supported. (Custom User settings that save over restarts of the addon)
local frame = CreateFrame("Frame")
frame:RegisterEvent("ADDON_LOADED")

frame:SetScript("OnEvent", function(self, event, ...)
    frame[event](self, ...) -- call event handlers
end)


SavedVar = {
	text = "teststring",
	initialized = 0,
}
function runtime() --Called whenever the hotkey is pressed from within the game
MSGPrint()
end


function getdescript(arg, filenameha)--Creates all the UI frames and creates a database entry upon clicking the "cap tags" button on the main frame of the addon

-- create a frame where we put our stuff
local f=CreateFrame("Frame",nil,QuestLogDetailScrollChildFrame)
f:SetBackdrop({
      bgFile="Interface\\DialogFrame\\UI-DialogBox-Background", 
      edgeFile="Interface\\DialogFrame\\UI-DialogBox-Border", 
      tile=1, tileSize=32, edgeSize=32, 
      insets={left=11, right=12, top=12, bottom=11}
})

f:IsResizable(true)

f:SetMaxResize(800, 400)
f:SetMinResize(400, 200)
f:SetScale(.85)
f:SetWidth(400)
f:SetHeight(250)
f:SetPoint("CENTER",UIParent)
f:EnableMouse(true)
f:SetMovable(true)
f:RegisterForDrag("LeftButton")
f:SetScript("OnDragStart", function(self) self:StartMoving() end)
f:SetScript("OnDragStop", function(self) self:StopMovingOrSizing() end)
f:SetFrameStrata("FULLSCREEN_DIALOG")

local button = CreateFrame("button","MyAddonButton", f, "UIPanelButtonTemplate")
button:SetHeight(32)
button:SetWidth(110)
button:SetPoint("BOTTOM", f, "BOTTOM", 0, 12)
button:SetText("Tag Screenshot")

local buttonsettings = CreateFrame("button","MyAddonButton", f, "UIPanelButtonTemplate")
buttonsettings:SetHeight(24)
buttonsettings:SetWidth(70)
buttonsettings:SetPoint("BOTTOMRIGHT", f, -25, 12)

buttonsettings:SetText("Settings")
buttonsettings:SetScript("OnClick", function(self) PlaySound("igMainMenuOption")  ChatFrame1:AddMessage('[SStagger]:Settings placeholder')  end)





local buttonexit = CreateFrame("button","MyAddonButton", f, "UIPanelButtonTemplate")
buttonexit:SetHeight(30)
buttonexit:SetWidth(30)
buttonexit:SetPoint("TOPRIGHT", f, -2, -2)

buttonexit:SetText(" X")
buttonexit:SetScript("OnClick", function(self) PlaySound("igMainMenuOption") self:GetParent():Hide() ChatFrame1:AddMessage('[SStagger]:Screenshot Taken But No Links Created')  end)

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
f.text4:SetText(format("SStagger v 0.69\n11/22/16\nkaylo mathrax swagchard"))
f.text4:SetPoint("BOTTOMLEFT", 15, 15)
f.text4:SetFont("Fonts\\FRIZQT__.TTF", 8)
f.text4:SetFontObject("ChatFontNormal")






local descr=CreateFrame("EditBox","EbT",f,"InputBoxTemplate") 

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
descr:SetScript("OnEditFocusGained", function(self) descr:SetText("") end)
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
tags:SetScript("OnEditFocusGained", function(self) tags:SetText("") end)


button:SetScript("OnClick", function(self) PlaySound("igMainMenuOption") self:GetParent():Hide()  text = descr:GetText() text2 = tags:GetText() tinsert(screenshotDB,"|"..text.."|"..text2..arg.."|") ChatFrame1:AddMessage("[SStagger]:File "..filenameha .." saved to drive with logs.") end)







end




function firsttime() --Upon first running the addon (When the game first loads or the UI is reset, the time is logged into the SStagger.lua file. This will allow for game sessions to be distinguished in the data sets in the future)

local timevar = format(date())
ChatFrame1:AddMessage("[SStagger]:Logged session start at "..timevar)
tinsert(screenshotDB, "SESSION START AT ".. timevar.." SAVED TO ../World of Warcraft/WTF/Account/354292016#1/SavedVariables/SStagger.lua.. " )
SavedVar.initialized = 1

end

function MSGPrint()

local description = ""
local timevar = format(date()) --Time variable which will be used to create a filename format that is identical to the default naming mechanism that the game uses in the Screenshot() Function
Screenshot()--built in function to take a SS and send it to the Wow/Screenshots directory
local playerName = UnitName("player")
local realmName =  GetRealmName()
local ZoneText =  GetRealZoneText()
local SubZone = GetSubZoneText ()
local filename = timevar:gsub("%/", "")
filename = filename:gsub(":", "")

filename = filename:gsub(" ", "_")
filename = "WoWScrnShot_" .. filename .. ".jpg"





--messagetext = ('Screenshot Saved for character'.. playerName .. ' on realm '.. realmName ..' at time: ' .. format(date()))




description = "|" .. filename .."|" .. timevar .. "|" .. playerName .. "|" .. realmName ..  "|" .. ZoneText.. "|" .. SubZone

getdescript(description, filename)


--textexample = "Screenshot Saved for character ".. playerName .. " on realm " ..  realmName .. " in ".. ZoneText.. " at ".. SubZone .. " at time: " .. timevar 
--message(textexample)





end

function frame:ADDON_LOADED(AddOn) --catches when the event of the addon being loaded is triggered, and runs this function
if SavedVar.initialized == 0 then
firsttime()
end
end
