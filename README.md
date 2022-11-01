# Bài tập lớn OOP - Bomberman Game
Phiên bản mô phỏng trò chơi Bomberman kinh điển của NES bằng ngôn ngữ JAVA
***
## Thông tin nhóm: Nhóm 2
* Nguyễn Văn Khang 21020768
* Nguyễn Thị Quỳnh Mai 21020125
***
## Mô tả các chức năng được cài đặt trong game
### Game gồm các chức năng:
* Tại màn hình chính: 
  * Xem và reset kỉ lục
  * Cài đặt âm thanh: tắt/bật
  * Hướng dẫn chơi game
  * Chọn level chơi: game gồm 3 level: EASY, MEDIUM, HARD

<img src="https://github.com/mairyy/TestJavaFx1/blob/main/Picture/mainMenu.png" width="180"> <img src="https://github.com/mairyy/TestJavaFx1/blob/main/Picture/highScore.png" width="180"> <img src="https://github.com/mairyy/TestJavaFx1/blob/main/Picture/setting.png" width="180"> <img src="https://github.com/mairyy/TestJavaFx1/blob/main/Picture/help.png" width="180"> <img src="https://github.com/mairyy/TestJavaFx1/blob/main/Picture/level.png" width="180">
* Tạm dừng màn chơi, cho phép lựa chọn chơi lại, tiếp tục hoặc trở về màn hình chính
<img src="https://github.com/mairyy/TestJavaFx1/blob/main/Picture/pause.png" width="180">

* Khi thắng có thể lựa chọn chơi lại màn chơi đó, chơi màn chơi kế tiếp hoặc trở về màn hình chính
<img src="https://github.com/mairyy/TestJavaFx1/blob/main/Picture/win.png" width="180">

* Khi thua có thể lựa chọn chơi lại màn chơi đó hoặc trở về màn hình chính
<img src="https://github.com/mairyy/TestJavaFx1/blob/main/Picture/lose.png" width="180">

* Tại màn hình game, phía trên sẽ hiện thị: level, điểm, thời gian còn lại, các `Item` đã ăn được và nút pause
<img src="https://github.com/mairyy/TestJavaFx1/blob/main/Picture/scoreBar.png" width="480">

### Mô tả luật chơi: 
* `Player` sẽ có một khoảng thời gian cố định để vượt tiêu diệt hết các `Enemy` và vượt qua màn chơi. 
* Càng tiêu diệt `Enemy` nhanh thì càng được cộng nhiều điểm. 
* Thời gian sẽ được đếm ngược và màn chơi sẽ kết thúc khi thời gian trở về 0 hoặc người chơi đã tiêu diệt hiệt `Enemy` và đi đến vị trí `Teleport`
* `Enemy` bị tiêu diệt khi đi vào phạm vi ảnh hưởng của `Bom`
* `Player` bị chết khi va chạm với `Enemy` hoặc đi vào phạm vi ảnh hưởng của `Bom`
### Các đối tượng trong game:
* <img src="https://github.com/mairyy/TestJavaFx1/blob/main/Picture/wall.png" width="25">`Wall` là đối tượng cố định, `Player` và các `Enemy` đều không thể đi qua, `Bom` cũng không thể đặt tại đây
* <img src="https://github.com/mairyy/TestJavaFx1/blob/main/Picture/grass.png" width="20">`Grass` là đối tượng mà `Player` và các `Enemy` có thể đi qua, `Bom` có thể đặt tại đây
* <img src="https://github.com/mairyy/TestJavaFx1/blob/main/Picture/brick.png" width="25">`Brick` là đối tượng có thể bị phá hủy bởi `Bom`, `Enemy` đặc biệt hoặc `Player` có `Item` đặc biệt có thể đi qua `Brick` khi nó chưa bị phá hủy
* <img src="https://github.com/mairyy/TestJavaFx1/blob/main/Picture/player.png" width="20"> `Player` là nhân vật chính của trò chơi, được người chơi điều khiển bằng 4 nút lên/xuống/trái/phải
* `Enemy` là các đối tượng mà Player phải tiêu diệt hết để có thể qua level tiếp theo. Các loại enemy được cài đặt trong game:
  * <img src="https://github.com/mairyy/TestJavaFx1/blob/main/Picture/enemy1.png" width="20">`Enemy 1` là enemy di chuyển ngẫu nhiên
  * <img src="https://github.com/mairyy/TestJavaFx1/blob/main/Picture/enemy2.png" width="20">`Enemy 2` là enemy thông minh có thể đuổi theo người
  * <img src="https://github.com/mairyy/TestJavaFx1/blob/main/Picture/enemy3.png" width="20">`Enemy 3` là enemy có thể đi xuyên `Brick`
  * <img src="https://github.com/mairyy/TestJavaFx1/blob/main/Picture/enemy4.png" width="20">`Enemy 4` là enemy có khả năng ăn `Bom`
* <img src="https://github.com/mairyy/TestJavaFx1/blob/main/Picture/teleport.png" width="20">`Teleport` là đối tượng được giấu phía sau `Brick`, khi `Brick` bị phá hủy, `Teleport` sẽ hiện ra và khi `Player` tiêu diệt hết các `Enemy` thì có thể di chuyển tới vị trí `Teleport` để qua level tiếp theo
* <img src="https://github.com/mairyy/TestJavaFx1/blob/main/Picture/bom.png" width="25">`Bom` là đối tượng mà `Player` có thể đặt tại các `Grass`. 
  * Khi đã được đặt, `Player` và `Enemy` không thể di chuyển vào vị trí `Bom`. 
  * Tuy nhiên ngay khi `Player` vừa đặt `Bom` tại ví trí của mình, `Player` có một lần được đi từ vị trí đặt `Bom` ra vị trí bên cạnh. 
  * Sau khi được đặt 3s, `Bom` sẽ nổ và <img src="https://github.com/mairyy/TestJavaFx1/blob/main/Picture/fire.png" width="25"> `fire` sẽ hiện ra ở 4 hướng trên/dưới/trái/phải (phạm vi ảnh hưởng của `Bom` thường là 1 ô tính từ vị trí đặt, tuy nhiên khi `Player` ăn được `Item` hỗ trợ, phạm vi ảnh hướng của `Bom` sẽ tăng lên 2 ô tính từ vị trí đặt)
  * Khi `fire` xuất hiện, nếu có `Wall`/`Brick` thì độ dài `fire` sẽ giảm đi để `fire` không xuất hiện ở vị trí `Wall`/`Brick`
* `Item` là các đối tượng được giấu sau `Brick`, sẽ xuất hiện khi `Brick` bị phá hủy, có tác dụng hỗ trợ `Player`. Các loại item được cài đặt trong game:
  * <img src="https://github.com/mairyy/TestJavaFx1/blob/main/Picture/addBom.png" width="25">`addBom` tăng số lượng `Bom` có thể đặt lên 1
  * <img src="https://github.com/mairyy/TestJavaFx1/blob/main/Picture/moveBrick.png" width="25">`moveBrick` giúp `Player` có khả năng đi xuyên `Brick`
  * <img src="https://github.com/mairyy/TestJavaFx1/blob/main/Picture/powerUpBom.png" width="25">`powerUpBom` làm tăng phạm vi ảnh hướng của `Bom`
  * <img src="https://github.com/mairyy/TestJavaFx1/blob/main/Picture/powerUpSpeed.png" width="25">`powerUpSpeed` tăng tốc độ của `Player`
  * <img src="https://github.com/mairyy/TestJavaFx1/blob/main/Picture/timeUp.png" width="25">`timeUp` cộng thêm thời gian của màn chơi
  * <img src="https://github.com/mairyy/TestJavaFx1/blob/main/Picture/antiBom.png" width="25">`antiBom` giúp `Player` bất tử khi gặp `Bom`
