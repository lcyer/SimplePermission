## What is This?
### Simple Permissions Library for Android
### Bring simple and easy permissions control to your app.

## How to Use?
### Java
```java
SimplePermission.builder()
                    .denyTitle("Permission Denied")
                    .denyMessage("please go to Permissions in the app settings and allow it.")
                    .permissions(Manifest.permission.CAMERA)
                    .create()
                    .check(new PermissionListener() {
    @Override
    public void onGranted() {
        new AlertDialog.Builder(PermissionTestForJava.this)
                .setMessage("Allow Camera Permission!")
                .setPositiveButton("ok", (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }

    @Override
    public void onDenied(@NonNull List<String> deniedPermissions) {

    }
});
```

### Kotlin with Coroutines
check function returns a PermissionResult Class.
PermissionResult  provides permission granted and a list of permission denied.

```kotlin
val permissionResult = SimplePermission.builder()
                        .denyTitle("Permission Denied")
                        .denyMessage("please go to Permissions in the app settings and allow it.")
                        .permissions(Manifest.permission.CAMERA)
                        .create()
                        .check()

isPermissionResult = permissionResult.isGranted
```

isGranted function lets you know right away if the permission is granted or not.
```kotlin
val isGranted = SimplePermission.builder()
    .denyTitle("Permission Denied")
    .denyMessage("please go to Permissions in the app settings and allow it.")
    .permissions(Manifest.permission.CAMERA)
    .create()
    .isGranted()

isPermissionResult = isGranted
```

## Next Planning?
### Currently, the only way to use the SimplePermission library is to attach modules to it, so we'll need to use the
### We will distribute the library through github packages in the future.