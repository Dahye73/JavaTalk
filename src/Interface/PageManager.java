package Interface;

public class PageManager {
    private static PageManager instance;
    private Profile_Page profilePage;
    private ChattingList_Page chattingListPage;
    private Setting_Page settingPage;

    private PageManager() {
        profilePage = new Profile_Page();
        chattingListPage = new ChattingList_Page();
        settingPage = new Setting_Page();
    }

    public static PageManager getInstance() {
        if (instance == null) {
            instance = new PageManager();
        }
        return instance;
    }

    public Profile_Page getProfilePage() {
        return profilePage;
    }

    public ChattingList_Page getChattingListPage() {
        return chattingListPage;
    }

    public Setting_Page getSettingPage() {
        return settingPage;
    }
}
