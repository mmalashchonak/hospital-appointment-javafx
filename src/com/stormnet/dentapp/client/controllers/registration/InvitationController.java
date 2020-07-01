package com.stormnet.dentapp.client.controllers.registration;

import com.stormnet.dentapp.client.common.AppWindows;
import com.stormnet.dentapp.client.common.Controller;
import com.stormnet.dentapp.client.common.WindowConfigs;

public class InvitationController implements Controller {


    public void clientBtnPressed() {
        AppWindows appWindows = AppWindows.getInstance();
        appWindows.hideWindow(WindowConfigs.InvitationWindow);
        appWindows.createWindow(WindowConfigs.ClientLoginWindow);
        appWindows.showWindow(WindowConfigs.ClientLoginWindow);
    }

    public void doctorBtnPressed() {
        AppWindows appWindows = AppWindows.getInstance();
        appWindows.hideWindow(WindowConfigs.InvitationWindow);
        appWindows.createWindow(WindowConfigs.DoctorLoginWindow);
        appWindows.showWindow(WindowConfigs.DoctorLoginWindow);
    }

    public void adminBtnPressed() {
        AppWindows appWindows = AppWindows.getInstance();
        appWindows.hideWindow(WindowConfigs.InvitationWindow);
        appWindows.createWindow(WindowConfigs.AdminLoginWindow);
        appWindows.showWindow(WindowConfigs.AdminLoginWindow);
    }

    @Override
    public void reloadWindow() {

    }

    @Override
    public void setFormObject(Object object) {

    }
}
