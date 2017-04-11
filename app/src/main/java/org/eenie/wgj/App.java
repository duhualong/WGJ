package org.eenie.wgj;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.eenie.wgj.di.component.ApplicationComponent;
import org.eenie.wgj.di.component.DaggerApplicationComponent;
import org.eenie.wgj.di.module.ApplicationModule;

import java.util.Stack;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * App
 */
public class App extends Application {

  private ApplicationComponent mApplicationComponent;
  private static Application sApplicationContext;
  private static Stack<Activity> sActivityStack;

  public static App get(Context context) {
    return (App)context.getApplicationContext();
  }

  @Override
  public void onCreate() {
    super.onCreate();
    mApplicationComponent = prepareApplicationComponent().build();
    mApplicationComponent.inject(this);
    sApplicationContext = this;

    sActivityStack = new Stack<>();
    Fresco.initialize(getApplicationContext());
    //sRefWatcher = LeakCanary.install(this);
    Realm.init(this);
    RealmConfiguration config = new  RealmConfiguration.Builder()
            .name("myRealm.realm")
            .deleteRealmIfMigrationNeeded()
            .build();
    Realm.setDefaultConfiguration(config);
  }

  protected DaggerApplicationComponent.Builder prepareApplicationComponent() {
    return DaggerApplicationComponent.builder()
        .applicationModule(new ApplicationModule(this));
  }

  public ApplicationComponent getApplicationComponent() {
    return mApplicationComponent;
  }

  public static void addActivity(Activity activity) {
    if (activity != null) {
      sActivityStack.add(activity);
    }
  }

  public static void clearStack() {
    if (sActivityStack != null && !sActivityStack.isEmpty()) {
      for (Activity activity : sActivityStack) {
        activity.finish();
      }
    }
  }
}
