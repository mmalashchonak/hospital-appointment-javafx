package com.stormnet.dentapp.client.controllers.registration;

import com.stormnet.dentapp.client.common.AppWindows;
import com.stormnet.dentapp.client.common.Controller;
import com.stormnet.dentapp.client.common.WindowConfigs;

public class WrongLoginPasswordController implements Controller {


    public void canselBtnPressed() {
        AppWindows appWindows = AppWindows.getInstance();
        appWindows.hideWindow(WindowConfigs.WrongLoginPasswordWindow);
    }

    @Override
    public void reloadWindow() {

    }

    @Override
    public void setFormObject(Object object) {

    }
}
