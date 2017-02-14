screenshotDB = screenshotDB or {} --Loads, or if not found, creates a db for outputting data logs.
local settings = {} -- Placeholder table for user settings that will eventually be supported. (Custom User settings that save over restarts of the addon)
 local items = {
   "500x250",
   "600x300",
   "700x350",
   "800x400",
   "900x450",
 }
  local behav = {
   "On Tag Screenshot press",
   "On Hotkey Press",
 }
 globalX = 400
 globalY = 200
globalfun = 0
local stng 
local frame = CreateFrame("Frame")
frame:RegisterEvent("ADDON_LOADED")

frame:SetScript("OnEvent", function(self, event, ...)
    frame[event](self, ...) -- call event handlers
end)


SavedVar = {
	resolutionsetting = 0,
	screencapsetting = 0,
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


f:SetScale(.85)
f:SetWidth(globalX)
f:SetHeight(globalY)
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
buttonsettings:SetScript("OnClick", function(self) PlaySound("igMainMenuOption")  settingsmenu(f) end)






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
stng:SetWidth(globalX)
stng:SetHeight(globalY * .75)
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
  
   local info = UIDropDownMenu_CreateInfo()
   for k,v in pairs(items) do
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
 end
  local function initialize2(self2, level2)
   local info2 = UIDropDownMenu_CreateInfo()

   for j,l in pairs(behav) do
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
globalX = 400 + (100* ((format(UIDropDownMenu_GetSelectedID(dropdown1)))))
globalY = 250 + (50 *     ((format(UIDropDownMenu_GetSelectedID(dropdown1)))-1   )      )
ChatFrame1:AddMessage(globalY)
parentpointer:SetWidth(globalX)
parentpointer:SetHeight(globalY)

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
