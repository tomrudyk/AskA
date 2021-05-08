Change Rules In Firebase to:

{
  "rules": {
    ".read": "auth !=null",
    ".write":"auth!=null"
  }
}
