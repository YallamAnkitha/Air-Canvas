import cv2
import mediapipe as mp
import numpy as np

# Initialize MediaPipe Hands
mp_hands = mp.solutions.hands
hands = mp_hands.Hands(max_num_hands=1, min_detection_confidence=0.7)
mp_draw = mp.solutions.drawing_utils

# Canvas for drawing
canvas = None
draw_color = (0, 0, 255)  # Red color
brush_thickness = 5

# Track previous fingertip position
prev_x, prev_y = 0, 0

# Start webcam
cap = cv2.VideoCapture(0)
 

while True:
    ret, frame = cap.read()
    if not ret:
        break

    frame = cv2.flip(frame, 1)  # Mirror image
    h, w, c = frame.shape

    if canvas is None:
        canvas = np.zeros((h, w, 3), dtype=np.uint8)

    # Convert to RGB for MediaPipe
    rgb_frame = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
    result = hands.process(rgb_frame)

    if result.multi_hand_landmarks:
        for hand_landmarks in result.multi_hand_landmarks:
            # Get index fingertip coordinates (landmark 8)
            x = int(hand_landmarks.landmark[8].x * w)
            y = int(hand_landmarks.landmark[8].y * h)

            # Draw circle at fingertip
            cv2.circle(frame, (x, y), 8, draw_color, -1)

            # Draw line on canvas
            if prev_x == 0 and prev_y == 0:
                prev_x, prev_y = x, y
            cv2.line(canvas, (prev_x, prev_y), (x, y), draw_color, brush_thickness)
            prev_x, prev_y = x, y

            mp_draw.draw_landmarks(frame, hand_landmarks, mp_hands.HAND_CONNECTIONS)
    else:
        prev_x, prev_y = 0, 0  # Reset if hand not detected

    # Combine canvas and frame
    combined = cv2.addWeighted(frame, 0.5, canvas, 0.5, 0)

    cv2.imshow("Air Canvas", combined)
    key = cv2.waitKey(1)

    if key == ord('c'):  # Clear canvas
        canvas = np.zeros((h, w, 3), dtype=np.uint8)
        print("🧼 Canvas cleared.")
    elif key == ord('s'):  # Save canvas
        filename = "air_canvas_output.png"
        cv2.imwrite(filename, canvas)
        print(f"✅ Drawing saved as {filename}")
    elif key == 27:  # ESC to exit
        print("👋 Exiting Air Canvas.")
        break

cap.release()
cv2.destroyAllWindows()

