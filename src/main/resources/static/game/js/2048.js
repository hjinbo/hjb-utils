$(function() {

    var board = new Array();
    var score = 0;
    var best = 0;

    init();

    $("#reset").click(function() {
        init();
    });

    document.addEventListener("keydown",keydown);

    function keydown(event){
        //表示键盘监听所触发的事件，同时传递参数event
        switch(event.keyCode){
            case 37:
                console.log(board);
                moveLeft(board);
                randomNum(board, 1);
                refresh(board);
                break;
            case 38:
                console.log(board);
                moveUp(board);
                randomNum(board, 1);
                refresh(board);
                break;
            case 39:
                console.log(board);
                moveRight(board);
                randomNum(board, 1);
                refresh(board);
                break;
            case 40:
                console.log(board);
                moveDown(board);
                randomNum(board, 1);
                refresh(board);
                break;
            default:
                break;
        }
    }

    function init () {
        for (var i = 0; i < 4; i++) {
            board[i] = new Array();
            for (var j = 0; j < 4; j++) {
                board[i][j] = 0;
            }
        }
        // 再随机生成两个数
        randomNum(board, 2);
        score = 0;
        for (var i = 0; i < 4; i++) {
            for (var j = 0; j < 4; j++) {
                if (board[i][j] !== 0) {
                    var idSelector = "#" + i + "-" + j;
                    $(idSelector).html("<span>" + board[i][j] + "</span>");
                }
            }
        }

        $("#test").empty();
        $("<span>" + board[0][0] + ",</span>").appendTo($("#test"));
        $("<span>" + board[0][1] + ",</span>").appendTo($("#test"));
        $("<span>" + board[0][2] + ",</span>,").appendTo($("#test"));
        $("<span>" + board[0][3] + "</span>").appendTo($("#test"));
        $("<br/>").appendTo($("#test"));
        $("<span>" + board[1][0] + ",</span>").appendTo($("#test"));
        $("<span>" + board[1][1] + ",</span>").appendTo($("#test"));
        $("<span>" + board[1][2] + ",</span>,").appendTo($("#test"));
        $("<span>" + board[1][3] + "</span>").appendTo($("#test"));
        $("<br/>").appendTo($("#test"));
        $("<span>" + board[2][0] + ",</span>").appendTo($("#test"));
        $("<span>" + board[2][1] + ",</span>").appendTo($("#test"));
        $("<span>" + board[2][2] + ",</span>,").appendTo($("#test"));
        $("<span>" + board[2][3] + "</span>").appendTo($("#test"));
        $("<br/>").appendTo($("#test"));
        $("<span>" + board[3][0] + ",</span>").appendTo($("#test"));
        $("<span>" + board[3][1] + ",</span>").appendTo($("#test"));
        $("<span>" + board[3][2] + ",</span>,").appendTo($("#test"));
        $("<span>" + board[3][3] + "</span>").appendTo($("#test"));
        $("<br/>").appendTo($("#test"));
    }

    function refresh (board) {
        $("#0-0").html("<span>" + board[0][0] + "</span>");
        $("#0-1").html("<span>" + board[0][1] + "</span>");
        $("#0-2").html("<span>" + board[0][2] + "</span>");
        $("#0-3").html("<span>" + board[0][3] + "</span>");

        $("#1-0").html("<span>" + board[1][0] + "</span>");
        $("#1-1").html("<span>" + board[1][1] + "</span>");
        $("#1-2").html("<span>" + board[1][2] + "</span>");
        $("#1-3").html("<span>" + board[1][3] + "</span>");

        $("#2-0").html("<span>" + board[2][0] + "</span>");
        $("#2-1").html("<span>" + board[2][1] + "</span>");
        $("#2-2").html("<span>" + board[2][2] + "</span>");
        $("#2-3").html("<span>" + board[2][3] + "</span>");

        $("#3-0").html("<span>" + board[3][0] + "</span>");
        $("#3-1").html("<span>" + board[3][1] + "</span>");
        $("#3-2").html("<span>" + board[3][2] + "</span>");
        $("#3-3").html("<span>" + board[3][3] + "</span>");

        $("#score").html(score);

        $("#test").empty();
        $("<span>" + board[0][0] + ",</span>").appendTo($("#test"));
        $("<span>" + board[0][1] + ",</span>").appendTo($("#test"));
        $("<span>" + board[0][2] + ",</span>,").appendTo($("#test"));
        $("<span>" + board[0][3] + "</span>").appendTo($("#test"));
        $("<br/>").appendTo($("#test"));
        $("<span>" + board[1][0] + ",</span>").appendTo($("#test"));
        $("<span>" + board[1][1] + ",</span>").appendTo($("#test"));
        $("<span>" + board[1][2] + ",</span>,").appendTo($("#test"));
        $("<span>" + board[1][3] + "</span>").appendTo($("#test"));
        $("<br/>").appendTo($("#test"));
        $("<span>" + board[2][0] + ",</span>").appendTo($("#test"));
        $("<span>" + board[2][1] + ",</span>").appendTo($("#test"));
        $("<span>" + board[2][2] + ",</span>,").appendTo($("#test"));
        $("<span>" + board[2][3] + "</span>").appendTo($("#test"));
        $("<br/>").appendTo($("#test"));
        $("<span>" + board[3][0] + ",</span>").appendTo($("#test"));
        $("<span>" + board[3][1] + ",</span>").appendTo($("#test"));
        $("<span>" + board[3][2] + ",</span>,").appendTo($("#test"));
        $("<span>" + board[3][3] + "</span>").appendTo($("#test"));
        $("<br/>").appendTo($("#test"));

    }

    function canMoveUp (board) {
        // 不考虑第一行
        for (var i = 1; i < 4; i++) {
            for (var j = 0; j < 4; j++) {
                if (board[i][j] !== 0) {
                    if (board[i - 1][j] === 0 || board[i - 1][j] === board[i][j]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    function canMoveDown (board) {
        // 不考虑第四行
        for (var i = 2; i >= 0; i--) {
            for (var j = 0; j < 4; j++) {
                if (board[i][j] !== 0) {
                    if (board[i + 1][j] === 0 || board[i + 1][j] === board[i][j]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    function canMoveLeft (board) {
        // 不考虑第一列
        for (var i = 0; i < 4; i++) {
            for (var j = 1; j < 4; j--) {
                if (board[i][j] !== 0) {
                    if (board[i][j - 1] === 0 || board[i][j - 1] === board[i][j]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    function canMoveRight (board) {
        // 不考虑第四列
        for (var i = 0; i < 4; i++) {
            for (var j = 2; j >= 0; j++) {
                if (board[i][j] != 0) {
                    if (board[i][j + 1] === 0 || board[i][j + 1] === board[i][j]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    function noBlockHorizontal (row, col1, col2, board) {
        var minCol = col1 < col2 ? col1 : col2;
        var maxCol = col1 >= col2 ? col1 : col2;
        for (var j = minCol + 1; j < maxCol; j++) {
            if (board[row][j] !== 0) {
                return false;
            }
        }
        return true;
    }

    function noBlockVertical (col, row1, row2, board) {
        var minRow = row1 < row2 ? row1 : row2;
        var maxRow = row1 >= row2 ? row1 : row2;
        for (var i = minRow + 1; i < maxRow; i++) {
            if (board[i][col] !== 0) {
                return false;
            }
        }
        return true;
    }

    function moveUp (board) {
        if (!canMoveUp(board)) {
            return;
        }
        console.log("上移");
        for (var i = 1; i < 4; i++) {
            for (var j = 0; j < 4; j++) {
                for (var t = 0; t < i; t++) {
                    if (board[t][j] === 0 && noBlockVertical(j, t, i, board)) {
                        board[t][j] = board[i][j];
                        board[i][j] = 0;
//                        hasConflict[t][j] = true;
                    } else if (board[t][j] === board[i][j] && noBlockVertical(j, t, i, board)) {
                        board[t][j] = board[i][j] * 2;
                        board[i][j] = 0;
                        score += board[t][j];
                    }
                }
            }
        }
    }

    function moveDown (board) {
        if (!canMoveDown(board)) {
            return;
        }
        console.log("下移");
        for (var i = 2; i >= 0; i--) {
            for (var j = 0; j < 4; j++) {
                for (var t = 3 ; t >= i; t--) {
                    if (board[t][j] === 0 && noBlockVertical(j, i, t, board)) {
                        board[t][j] = board[i][j];
                        board[i][j] = 0;
                    } else if (board[t][j] === board[i][j] && noBlockVertical(j, i, t, board)) {
                        board[t][j] = board[i][j] * 2;
                        board[i][j] = 0;
                    }
                }
            }
        }
    }

    function moveLeft (board) {
        if (!canMoveLeft(board)) {
            return;
        }
        console.log("左移");
        for (var i = 0; i < 4; i++) {
            for (var j = 1; j < 4; j++) {
                for (var t = 0; t < j; t++) {
                    if (board[i][t] === 0 && noBlockHorizontal(i, j, t, board)) {
                        board[i][t] = board[i][j];
                        board[i][j] = 0;
                    } else if (board[i][t] === board[i][j] && noBlockHorizontal(i, j, t, board)) {
                        board[i][t] = board[i][j] * 2;
                        board[i][j] = 0;
                    }
                }
            }
        }
    }

    function moveRight (board) {
        if (!canMoveRight(board)) {
            return;
        }
        console.log("右移");
        for (var i = 0; i < 4; i++) {
            for (var j = 2; j >= 0; j--) {
                for (var t = 3; t > j; t--) {
                    if (board[i][t] === 0 && noBlockHorizontal(i, j, t, board)) {
                        board[i][t] = board[i][j];
                        board[i][j] = 0;
                    } else if (board[i][t] === board[i][j] && noBlockHorizontal(i, j, t, board)) {
                        board[i][t] = board[i][j] * 2;
                        board[i][j] = 0;
                    }
                }
            }
        }
    }

    function randomNum (board, count) {
        while (count > 0) {
            var x = Math.floor(Math.random() * 4);
            var y = Math.floor(Math.random() * 4);
            if (board[x][y] === 0) {
                board[x][y] = Math.random() > 0.5 ? 2 : 4;
                var idSelector = "#" + x + "-" + y;
                $(idSelector).attr("class", "chessman animated bounce flipInY");
                setTimeout(function () { $(idSelector).attr("class", "chessman"); }, 300);
                count--;
            }
        }
    }
})