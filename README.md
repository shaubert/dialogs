# Dialogs
Android FragmentDialog builders

## Gradle

    repositories {
        maven{url "https://github.com/shaubert/maven-repo/raw/master/releases"}
    }
    dependencies {
        compile 'com.shaubert.ui.dialogs:library:1.2'
    }
    
## Android

Required Android API >= 14

## Supported Dialogs

  * `AlertDialogManager`
  * `DatePickerDialogManager`
  * `TimePickerDialogManager`
  * `EditTextDialogManager`
  * `ListDialogManager`
  * `ProgressDialogManager`

## Example
    
    AlertDialogManager alertDialog = new AlertDialogManager(fragmentActivity, "confirmDeleteDialog"); //tag should be unique, used as fragment tag
    alertDialog.setTitle("Confirm");
    alertDialog.setMessage("Do you want to remove all elements? This action is undone.");
    alertDialog.setCancellable(true);
    alertDialog.setPosButton("Yes", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            removeItems();
        }
    });
    alertDialog.setNegButtonText("Cancel");
    alertDialog.showDialog();
    
