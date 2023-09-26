import { setCheckedItemsToValue, displayModal } from './common.js';

const noneCheckbox = document.getElementById("none-checkbox");
const doneEnterBtn = document.getElementById("done-enter-btn");

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
