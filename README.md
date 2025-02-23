# zenQR
ZenQR – Effortlessly convert text and images to QR codes and scan them back with ease

# QR Code Generator & Scanner App

## Overview
This is an Android application that allows users to generate QR codes from text or images and scan QR codes using the device's camera. It features a clean UI, a shimmer loading effect, and additional functionalities like feedback submission and contact information.

## Features
- **Generate QR Codes**: Convert text or images into QR codes.
- **Scan QR Codes**: Scan and decode QR codes using the camera.
- **Shimmer Effect**: Displays a shimmer loading effect while loading data.
- **User Feedback**: Users can submit feedback about the app.
- **Contact Section**: Provides contact information for queries.
- **Exit Confirmation**: Custom exit dialog to confirm app closure.

## Tech Stack
- **Language**: Kotlin
- **Framework**: Android SDK
- **UI Components**: ConstraintLayout, CardView, ShimmerFrameLayout, Toolbar
- **Third-party Libraries**:
  - Facebook Shimmer for loading effects

## Installation
1. Clone the repository:
   ```sh
   git clone https://github.com/yourusername/qrcodegenerator.git
   ```
2. Open the project in **Android Studio**.
3. Sync Gradle and build the project.
4. Run the app on an emulator or physical device.

## Project Structure
```
/app
  ├── src
  │   ├── main
  │   │   ├── java/com/example/qrcodegenerator
  │   │   │   ├── MenuActivity.kt
  │   │   │   ├── TexttoQRActivity.kt
  │   │   │   ├── ImagetoQRActivity.kt
  │   │   │   ├── ScanActivity.kt
  │   │   │   ├── FeedBackActivity.kt
  │   │   │   ├── ContactActivity.kt
  │   │   ├── res/layout
  │   │   │   ├── activity_menu.xml
  │   │   │   ├── activity_feedback.xml
  │   │   │   ├── custom_popup.xml
  │   │   ├── res/menu
  │   │   │   ├── main_menu.xml
```

## Known Issues
- **NullPointerException in FeedBackActivity**: Ensure `setContentView(R.layout.activity_feedback)` is called and the required views exist.
- **Shimmer effect not stopping**: Check if `shimmerViewContainer.stopShimmer()` is correctly placed.

## Contribution
1. Fork the repository.
2. Create a new branch (`feature/your-feature`).
3. Commit your changes.
4. Push to the branch and submit a pull request.

## License
This project is licensed under the MIT License.

---
For any issues, feel free to create a GitHub issue or contact us via the Contact section in the app.

