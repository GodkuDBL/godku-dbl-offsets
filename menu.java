// Assuming you're using Frida for hooking

Java.perform(function() {
    var dbLegendsClass = Java.use('com.bandainamcoen.dblegends_ww.GameUtils');

    // Assuming 0x12A52A4 is the offset for get_stones()
    var getStonesOffset = 0x12A52A4;

    var getStonesAddress = Module.findBaseAddress("libil2cpp.so").add(getStonesOffset);

    Interceptor.attach(getStonesAddress, {
        onEnter: function(args) {
            console.log("Hooked get_stones method for DB Legends");
        },
        onLeave: function(retval) {
            retval.replace(999999999999999);
        }
    });

    // Now let's create a mod menu with a floating icon
    var Context = Java.use('android.content.Context');
    var Intent = Java.use('android.content.Intent');
    var View = Java.use('android.view.View');
    var WindowManager = Java.use('android.view.WindowManager');
    var ImageView = Java.use('android.widget.ImageView');
    var TextView = Java.use('android.widget.TextView');
    var Toast = Java.use('android.widget.Toast');
    var Gravity = Java.use('android.view.Gravity');

    var activityThread = Java.use('android.app.ActivityThread');
    var currentActivityThread = activityThread.currentActivityThread();
    var context = currentActivityThread.getApplication();

    var windowManager = context.getSystemService(Context.WINDOW_SERVICE);
    var params = WindowManager.LayoutParams();

    var iconView = ImageView(context);
    iconView.setImageResource(context.getResources().getIdentifier("icon", "drawable", "com.your.package.name"));

    params.width = WindowManager.LayoutParams.WRAP_CONTENT;
    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
    params.gravity = Gravity.RIGHT | Gravity.TOP;
    params.x = 0;
    params.y = 100;

    windowManager.addView(iconView, params);

    iconView.setOnClickListener(new View.OnClickListener({
        onClick: function(view) {
            // Display your mod menu here
            console.log("Mod menu clicked");
            var toast = Toast.makeText(context, "Stones: " + dbLegendsClass.get_stones(), Toast.LENGTH_LONG);
            toast.show();
        }
    }));
});
