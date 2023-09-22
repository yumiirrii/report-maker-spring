import { setCheckedItemsToValue, displayModal } from './common.js';

const noneCheckbox = document.getElementById("none-checkbox");
const doneEnterBtn = document.getElementById("done-enter-btn");

//noneCheckbox.addEventListener("change", function() {
//    noneCheckbox.value = true;
//})


//function uncheckedNoneCheckbox() {
////    noneDisplay.style.display = "block";
////    if(listItem) {
////        listItem.remove();
////    }
//    noneCheckbox.value = false;
//}
//function checkedNoneCheckbox() {
////    noneDisplay.style.display = "none";
////    if (doneTaskLi) {
////        doneTaskLi.remove();
////    }
////    doneTaskUl.appendChild(listItem);
////    listItem.appendChild(spanItem);
//    noneCheckbox.value = true;
////    doneEnterBtn.addEventListener("click", function() {
////        noneDisplay.style.display = "none";
////    })
//}
/* チェックボックス */
const selectedLastPlanningTasks = document.getElementById("selectedLastPlanningTasks");
setCheckedItemsToValue(selectedLastPlanningTasks);


/* モーダル */
const modal = document.getElementById("modal");
const registerTasksBtn = document.getElementById("register-tasks-btn");
modal.style.display = "none";
registerTasksBtn.addEventListener("click", function() {
    displayModal(modal);
});

/* 残り文字数カウント */
//let inputArea = document.getElementById("input-summary");
//inputArea.addEventListener("keyup", function() {
//    countRemainCharNum(inputArea);
//});

