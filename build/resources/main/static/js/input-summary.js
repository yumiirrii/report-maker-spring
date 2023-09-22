import { countRemainCharNum } from './common.js';

/* 残り文字数カウント */
let inputArea = document.getElementById("input-summary");
inputArea.addEventListener("keyup", function() {
    countRemainCharNum(inputArea);
});

