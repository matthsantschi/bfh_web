const morseAlphabetString =
    "a=.-;b=-...;c=-.-.;d=-..;e=.;f=..-;g=--.;h=....;i=..;j=.---;k=-.-;l=.-..;" +
    "m=--;n=-.;o=---;p=.---.;q=--.-;r=.-.;s=...;t=-;u=..-;v=...-;w=.--;x=-..-;" +
    "y=-.-;z=--..; =;.=.-.-.-;,=--..--;?=..--..";

const morseAlphabetObj = function (morseAlphabetString) {
    let morseAlphabetObj = {};
    morseAlphabetString.split(";").forEach(element => {
        let elmentKeyVal = element.split("=");
        morseAlphabetObj[elmentKeyVal[0]] = elmentKeyVal[1];
    });
    return morseAlphabetObj;
}
const alphabet = morseAlphabetObj(morseAlphabetString);


const encode = (dictObj, string) => {
    this.encodeChar = function (dictObj, char) { return alphabet[char]; }

    var morseCode = ""
    for (let index = 0; index < string.length; index++) {
        morseCode += this.encodeChar(dictObj, string[index]) + "STOP";
    }
    return morseCode;
}

const translateButton = document.getElementById("js_translate");

translateButton.addEventListener("click", function (event) {
    event.preventDefault();
    let textToTranslate = document.getElementById("js_textToTranslate")
    let translationDiv = document.getElementById("js_translationDiv")
    translationDiv.innerHTML = encode(alphabet, textToTranslate.value);
});