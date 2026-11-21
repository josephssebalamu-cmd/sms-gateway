# sms-gateway

my vivo sms gateway

This Flutter Android app receives product commands over SMS and processes
outbound replies through Supabase. The app UI is for gateway status, messages,
and settings; product catalog management is performed by SMS.

## GitHub Actions

The `Build split-per-ABI release APK` workflow runs on pushes to `main` and can
also be started manually. Configure these repository Actions secrets before
building:

- `SUPABASE_URL`
- `SUPABASE_SERVICE_ROLE_KEY`

The release signing secrets are optional for an internal build. Without them,
the APK is signed with the Android debug key and is not suitable for publishing.

The service-role key bypasses Row Level Security and is embedded in the APK.
Do not distribute such a build publicly.
