;
; NSIS script om de Finan installer te genereren.
;

!include LogicLib.nsh

;--------------------------------

!macro BIMAGE IMAGE
	Push $0
	GetTempFileName $0
	File /oname=$0 "${IMAGE}"
	SetBrandingImage $0
	Delete $0
	Pop $0
!macroend


;--------------------------------

Name "Finan"
Caption "Finan"

OutFile	"..\..\target\FinanInstaller.exe"

LicenseText "Om Finan te kunnen gebruiken, moet je akkoord gaan met de onderstaande voorwaarden."
LicenseData "FinanLicense.rtf"


InstallColors 101010 e0e0e0
XPStyle on

; Add branding image to the installer (an image placeholder on the side).
; It is not enough to just add the placeholder, we must set the image too...
; We will later set the image in every pre-page function.
; We can also set just one persistent image in .onGUIInit
AddBrandingImage left 50 10

InstallDir       "$PROGRAMFILES\Finan"

; Registry key to check for directory (so if you install again, it will 
; overwrite the old one automatically)
#InstallDirRegKey HKLM "Software\Finan" "Install_Dir"

LoadLanguageFile "${NSISDIR}\Contrib\Language files\Dutch.nlf"

;--------------------------------

Page license setBrandingImage
Page components
Page directory
Page custom showUserSettings leaveUserSettings
Page instfiles "" "" readySound

UninstPage uninstConfirm
UninstPage instfiles

;--------------------------------


Section "" ; empty string makes it hidden.

  ; write reg info
;  DetailPrint "De plug-ins dir. is $PLUGINSDIR"
  ; Write the installation path into the registry
  #WriteRegStr HKLM SOFTWARE\Finan "InstallDir" "$INSTDIR"

  ; write uninstall keys for Windows
  #WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\Finan" "DisplayName" "Finan (remove only)"
  #WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\Finan" "UninstallString" '"$INSTDIR\Finan-uninst.exe"'
  #WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\Finan" "DisplayIcon" '"$INSTDIR\FinanLogo.ico"'
  #WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\Finan" "NoModify" 1
  #WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\Finan" "NoRepair" 1

  SetOutPath $INSTDIR
  WriteUninstaller "Finan-uninst.exe"  
SectionEnd


Section "!Finan programma"
  SectionIn RO

  File "FinanLogo.ico"
  File "..\..\target\goedegep-finan-1.0-SNAPSHOT.jar"
  File "C:\Documents and Settings\Peter\.m2\repository\antlr\antlr\2.7.6\antlr-2.7.6.jar"
  File "C:\Documents and Settings\Peter\.m2\repository\commons-collections\commons-collections\2.1.1\commons-collections-2.1.1.jar"
  File "C:\Documents and Settings\Peter\.m2\repository\commons-logging\commons-logging\1.0.4\commons-logging-1.0.4.jar"
  File "C:\Documents and Settings\Peter\.m2\repository\dom4j\dom4j\1.6.1\dom4j-1.6.1.jar"
  File "C:\Documents and Settings\Peter\.m2\repository\goedegep\goedegep-admin\1.0-SNAPSHOT\goedegep-admin-1.0-SNAPSHOT.jar"
  File "C:\Documents and Settings\Peter\.m2\repository\goedegep\goedegep-appgen\1.0-SNAPSHOT\goedegep-appgen-1.0-SNAPSHOT.jar"
  File "C:\Documents and Settings\Peter\.m2\repository\goedegep\goedegep-config-model\1.0-SNAPSHOT\goedegep-config-model-1.0-SNAPSHOT.jar"
  File "C:\Documents and Settings\Peter\.m2\repository\goedegep\goedegep-finan-help\1.0-SNAPSHOT\goedegep-finan-help-1.0-SNAPSHOT.jar"
  File "C:\Documents and Settings\Peter\.m2\repository\goedegep\goedegep-properties\1.0-SNAPSHOT\goedegep-properties-1.0-SNAPSHOT.jar"
  File "C:\Documents and Settings\Peter\.m2\repository\goedegep\goedegep-properties-model\1.0-SNAPSHOT\goedegep-properties-model-1.0-SNAPSHOT.jar"
  File "C:\Documents and Settings\Peter\.m2\repository\goedegep\goedegep-util\1.0-SNAPSHOT\goedegep-util-1.0-SNAPSHOT.jar"
  File "C:\Documents and Settings\Peter\.m2\repository\goedegep\goedegep-types-model\1.0-SNAPSHOT\goedegep-types-model-1.0-SNAPSHOT.jar"
  File "C:\Documents and Settings\Peter\.m2\repository\javax\jh\jhall\2.0_05\jhall-2.0_05.jar"
  File "C:\Documents and Settings\Peter\.m2\repository\javax\transaction\jta\1.0.1B\jta-1.0.1B.jar"
  File "C:\Documents and Settings\Peter\.m2\repository\jfree\jfreechart\1.0.13\jfreechart-1.0.13.jar"
  File "C:\Documents and Settings\Peter\.m2\repository\hsqldb\hsqldb\1.8.0.1\hsqldb-1.8.0.1.jar"
  File "C:\Documents and Settings\Peter\.m2\repository\org\hibernate\hibernate\3.2.0.ga\hibernate-3.2.0.ga.jar"
  File "C:\Documents and Settings\Peter\.m2\repository\org\springframework\spring\2.0.2\spring-2.0.2.jar"
  File "C:\Documents and Settings\Peter\.m2\repository\plexus\plexus-utils\1.0.3\plexus-utils-1.0.3.jar"
  File "C:\Documents and Settings\Peter\.m2\repository\xerces\xercesImpl\2.8.1\xercesImpl-2.8.1.jar"
  File "C:\Documents and Settings\Peter\.m2\repository\org\eclipse\emf\org.eclipse.emf.common\2.8.0.v20120606-0717\org.eclipse.emf.common-2.8.0.v20120606-0717.jar"
  File "C:\Documents and Settings\Peter\.m2\repository\org\eclipse\emf\org.eclipse.emf.ecore\2.8.0.v20120606-0717\org.eclipse.emf.ecore-2.8.0.v20120606-0717.jar"
  File "C:\Documents and Settings\Peter\.m2\repository\org\eclipse\emf\org.eclipse.emf.ecore.xmi\2.8.0.v20120606-0717\org.eclipse.emf.ecore.xmi-2.8.0.v20120606-0717.jar"
  CreateDirectory "data"
  ReadINIStr $1 "$PLUGINSDIR\FinanUserSettings.ini" "Field 3" "State"
  CreateShortCut '$INSTDIR\Finan.lnk' 'javaw.exe' \
                 '-jar goedegep-finan-1.0-SNAPSHOT.jar "$1"' '$INSTDIR\FinanLogo.ico' '' SW_SHOWNORMAL '' 'Start Finan'
SectionEnd


Section "Configuratie bestanden"
  File "..\main\resources\FinanDefault.properties"
  File "..\main\resources\FinanCustom.properties"

  ;Create default properties file.
;  FileOpen $0 $INSTDIR\FinanDefault.properties w
;  FileWrite $0 "#--- Finan Default properties---"
;  FileWriteByte $0 "13"
;  FileWriteByte $0 "10"
;  FileWrite $0 "companiesFile=Companies.xml"
;  FileWriteByte $0 "13"
;  FileWriteByte $0 "10"
;  FileWrite $0 "companyFundsFile=CompanyFunds.xml"
;  FileWriteByte $0 "13"
;  FileWriteByte $0 "10"
;  FileWrite $0 "fundSharesFile=FundShares.xml"
;  FileWriteByte $0 "13"
;  FileWriteByte $0 "10"
;  FileWrite $0 "shareDividendsFile=shareDividends.xml"
;  FileWriteByte $0 "13"
;  FileWriteByte $0 "10"
;  FileClose $0

;  FileOpen $0 $INSTDIR\FinanCustom.properties w
;  FileWrite $0 "#--- Finan Custom properties ---"
;  FileWriteByte $0 "13"
;  FileWriteByte $0 "10"
;  FileWrite $0 "transactionsFile=FinanTransactions.txt"
;  FileWriteByte $0 "13"
;  FileWriteByte $0 "10"
;  FileClose $0

SectionEnd

Section "data bestanden"
;  File "..\main\resources\populateFamilyTables.sql"
;  File "..\main\resources\populateInstitutionTables.sql"
;  File "..\main\resources\populatePhoneAddressBookTables.sql"
;  File "..\main\resources\populatePhoneNumberTables.sql"
;  File "..\main\resources\populateTableAddress.sql"
;  File "..\main\resources\populateTableCity.sql"
;  File "..\main\resources\populateTableCountry.sql"
;  File "..\main\resources\populateTablePerson.sql"
;  File "..\main\resources\populateTableProvider.sql"
  File "..\main\resources\goedegep\finan\aanstelling\Aanstelling.xml"
  File "..\main\resources\goedegep\finan\aanstelling\Aanstelling.xsd"
  File "..\main\resources\goedegep\finan\basic\FinancieleEenheden.xml"
  File "..\main\resources\goedegep\finan\basic\FinancieleEenheden.xsd"
  File "..\main\resources\goedegep\finan\hypotheek\Hypotheken.xml"
  File "..\main\resources\goedegep\finan\hypotheek\Hypotheken.xsd"
  File "..\main\resources\goedegep\finan\stocks\BelastingKoersen.xml"
  File "..\main\resources\goedegep\finan\stocks\BelastingKoersen.xsd"
  File "..\main\resources\goedegep\finan\stocks\ClaimEmissions.xml"
  File "..\main\resources\goedegep\finan\stocks\ClaimEmissions.xsd"
  File "..\main\resources\goedegep\finan\stocks\Companies.xml"
  File "..\main\resources\goedegep\finan\stocks\Companies.xsd"
  File "..\main\resources\goedegep\finan\stocks\CompanyFunds.xml"
  File "..\main\resources\goedegep\finan\stocks\CompanyFunds.xsd"
  File "..\main\resources\goedegep\finan\stocks\FundShares.xml"
  File "..\main\resources\goedegep\finan\stocks\FundShares.xsd"
  File "..\main\resources\goedegep\finan\stocks\OptionSeries.xml"
  File "..\main\resources\goedegep\finan\stocks\OptionSeries.xsd"
  File "..\main\resources\goedegep\finan\stocks\ShareDividends.xml"
  File "..\main\resources\goedegep\finan\stocks\ShareDividends.xsd"
SectionEnd

sectionGroup snelkoppelingen

  Section "Koppeling in Start menu"
    CreateDirectory "$SMPROGRAMS\Finan"
    ReadINIStr $1 "$PLUGINSDIR\FinanUserSettings.ini" "Field 3" "State"
    CreateShortCut '$SMPROGRAMS\Finan\Finan.lnk' 'javaw.exe' \
                 '-jar goedegep-finan-1.0-SNAPSHOT.jar "$1"' '$INSTDIR\FinanLogo.ico' '' SW_SHOWNORMAL '' 'Start Finan'
    CreateShortCut "$SMPROGRAMS\Finan\Finan-uninst.lnk" "$INSTDIR\Finan-uninst.exe" \
                   "" "$INSTDIR\Finan-uninst.exe" "" SW_SHOWNORMAL "" "Verwijder Finan"
  SectionEnd

  Section "snelkoppeling op het bureaublad"
    ReadINIStr $1 "$PLUGINSDIR\FinanUserSettings.ini" "Field 3" "State"
    CreateShortCut '$DESKTOP\Finan.lnk' 'javaw.exe' \
                 '-jar goedegep-finan-1.0-SNAPSHOT.jar "$1"' '$INSTDIR\FinanLogo.ico' '' SW_SHOWNORMAL '' 'Start Finan'
  SectionEnd

  Section "Quick access icon"
    ReadINIStr $1 "$PLUGINSDIR\FinanUserSettings.ini" "Field 3" "State"
    CreateShortCut '$QUICKLAUNCH\Finan.lnk' 'javaw.exe' \
                 '-jar goedegep-finan-1.0-SNAPSHOT.jar "$1"' '$INSTDIR\FinanLogo.ico' '' SW_SHOWNORMAL '' 'Start Finan'
  SectionEnd

sectionGroupEnd

Function .onInit
  # the plugins dir is automatically deleted when the installer exits
  InitPluginsDir

  ;StrCpy $INSTDIR "C:\Program Files\Finan"
  File /oname=$PLUGINSDIR\FinanUserSettings.ini "FinanUserSettings.ini"
FunctionEnd

Function .onGUIInit
  # lets extract some bitmaps...
  File /oname=$PLUGINSDIR\Finan.bmp "FinanSplashScreen.bmp"
  File /oname=$PLUGINSDIR\notify.wav "notify.wav"

  # set the initial background for images to be drawn on
  # we will use a gradient from dark blue to a bright blue
  BgImage::SetBg /NOUNLOAD /GRADIENT 0x0 0x0 0x50 0x10 0x10 0xFF

  # create the font for the following text
  CreateFont $R0 "Times New Roman" 50 700
  # add a text with shadow
  BgImage::AddText /NOUNLOAD "Finan voor al uw financien" $R0 50 50 50 128 48 900 198
  BgImage::AddText /NOUNLOAD "Finan voor al uw financien" $R0 80 80 80 127 49 899 199
  BgImage::AddText /NOUNLOAD "Finan voor al uw financien" $R0 110 110 110 126 50 898 200
  BgImage::AddText /NOUNLOAD "Finan voor al uw financien" $R0 150 150 150 125 51 897 201
  BgImage::AddText /NOUNLOAD "Finan voor al uw financien" $R0 190 190 190 124 52 896 202

  # add the Finan image @ (150,200)
  BgImage::AddImage /NOUNLOAD $PLUGINSDIR\Finan.bmp 150 200

  # show our creation to the world!
  BgImage::Redraw /NOUNLOAD
  # Refresh doesn't return any value
	
FunctionEnd


Function .onGUIEnd
  # Destroy must not have /NOUNLOAD so NSIS will be able to unload
  # and delete BgImage before it exits
  BgImage::Destroy
FunctionEnd


Function setBrandingImage
	!insertmacro BIMAGE "FinanLogo.bmp"
FunctionEnd

Function ShowUserSettings

  ; Initialise the dialog but don't show it yet
  InstallOptions::initDialog /NOUNLOAD "$PLUGINSDIR\FinanUserSettings.ini"
  ; In this mode InstallOptions returns the window handle so we can use it
  Pop $0
  ; Now show the dialog and wait for it to finish
  InstallOptions::show
  ; Finally fetch the InstallOptions status value (we don't care what it is though)
  Pop $0

FunctionEnd

Function leaveUserSettings
  ReadINIStr $0 "$PLUGINSDIR\FinanUserSettings.ini" "Field 3" "HWND"
  DetailPrint "De data directory is $0"
FunctionEnd


Function readySound
  BgImage::Sound /NOUNLOAD /WAIT $PLUGINSDIR\notify.wav
FunctionEnd

;--------------------------------

; Uninstaller

UninstallText "Dit programma verwijdert Finan van de computer. Klik op 'verwijderen' om verder te gaan."

Section "Uninstall"
  Delete "$INSTDIR\Finan.jar"
  Delete "$INSTDIR\FinanLogo.ico"
  Delete "$INSTDIR\Finan.lnk"
  Delete "$INSTDIR\Finan-uninst.exe"
  Delete "$INSTDIR\FinanDefault.properties"
  Delete "$INSTDIR\FinanCustom.properties"
  Delete "$INSTDIR\data\*.*"
  RMDir "$INSTDIR\data"
  RMDir "$INSTDIR"
  Delete "$SMPROGRAMS\Finan\Finan.lnk"
  Delete "$SMPROGRAMS\Finan\Finan-uninst.lnk"
  RMDir  "$SMPROGRAMS\Finan"
  Delete "$DESKTOP\Finan.lnk"
  Delete "$QUICKLAUNCH\Finan.lnk"

  IfFileExists "$INSTDIR" 0 InstDirRemoved
    MessageBox MB_OK "Note: $INSTDIR kon niet verwijderd worden!" IDOK 0
  InstDirRemoved:

  IfFileExists "$SMPROGRAMS\Finan" 0 FinanDirRemoved
    MessageBox MB_OK "Note: $SMPROGRAMS\Finan kon niet verwijderd worden!" IDOK 0
  FinanDirRemoved:

  DeleteRegKey HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\Finan"
  DeleteRegKey HKLM "SOFTWARE\Finan"
SectionEnd

