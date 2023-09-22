import { setCheckedItemsToValue, countRemainCharNum, displayModal } from './common.js';

/* PROJECT.DELETEチェックボックス */
const deleteProjectAndTasks = document.getElementById("deleteProjectAndTasks");
setCheckedItemsToValue(deleteProjectAndTasks);

/* SUMMARY.EDIT　*/
const confirmSummary = document.getElementById("confirm-summary");
const summaryText = document.getElementById("summary-text");
const confirmBtn = document.getElementById("confirm-btn");
const editSummary = document.getElementById("edit-summary");
let inputArea = document.getElementById("input-summary");
const editBtn = document.getElementById("edit-btn");
const submitBtn = document.getElementById("submit-btn");
editSummary.style.display = "none";

/* SUMMARY.EDITボタン押下 */
editBtn.addEventListener("click", function() {
    confirmSummary.style.display = "none";
    submitBtn.style.display = "none";
    editSummary.style.display = "block";
    inputArea.textContent = summaryText.textContent;
    /* 入力済み残り文字数カウント */
    countRemainCharNum(inputArea);
})

/* SUMMARY.EDITモード */
    inputArea.addEventListener("keyup", function() {
        /* 残り文字数カウント */
        countRemainCharNum(inputArea);
    });

if (editSummary.style.display === "block") {
    confirmBtn.addEventListener("click", function() {
        confirmSummary.style.display = "block";
        submitBtn.style.display = "block";
        editSummary.style.display = "none";
        summaryText.textContent = inputArea.textContent;
    })
}

/* REPORT SUBMIT後モーダル */
const confirmModal = document.getElementById("confirm-modal");
confirmModal.style.display = "none";
if (result) {
    displayModal(confirmModal);
}