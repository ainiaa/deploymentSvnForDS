#Region ;**** ���������� ACNWrapper_GUI ****
#PRE_Icon=bitbug_favicon.ico
#PRE_Compile_Both=y
#PRE_Res_requestedExecutionLevel=None
#EndRegion ;**** ���������� ACNWrapper_GUI ****
If FileExists("DeploymentSvnForDS.jar.new") Then
	FileDelete("DeploymentSvnForDS.jar")
	FileMove("DeploymentSvnForDS.jar.new", "DeploymentSvnForDS.jar")
EndIf

Run('cmd /c start javaw -cp ./DeploymentSvnForDS.jar com.coding91.utility.ControllerJFrame', "", @SW_HIDE);