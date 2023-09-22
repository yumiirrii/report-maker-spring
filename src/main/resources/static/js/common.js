/* チェックボックス */
export function setCheckedItemsToValue(submitId) {
    const checkboxItems = document.querySelectorAll(".checkbox-item");

    checkboxItems.forEach((checkboxItem) => {
        checkboxItem.addEventListener("change", function() {
            const selectedItemsArray = [];
            checkboxItems.forEach((checkboxItem) => {
                if(checkboxItem.checked) {
                    selectedItemsArray.push(checkboxItem.value);
                }
            });
        submitId.value = selectedItemsArray.join(",");
        });
    });
}

/* 残り文字数カウント */
export function countRemainCharNum(inputArea) {
    let now = null,
        max = 500,
        outputArea = document.getElementById("text-length");

    now = ( max - inputArea.value.length );
    outputArea.innerText = now;
    outputArea.className = ( now < 0 ) ? "warning" : "";
}

/* モーダル */
export function displayModal(modal) {
   modal.style.display = "block";
   modal.classList.add("modal");
}
