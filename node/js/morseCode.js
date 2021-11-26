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
    for (const char of string.toLowerCase()) {
        morseCode += this.encodeChar(dictObj, char) + "STOP";
    }
    return morseCode;
}

console.log(encode(alphabet, "Bla Bla"));

translateButton = document.getElementById("js_translate");

translateButton.addEventListener("click", function (event) {
    event.preventDefault();
    console.log("button clicked");
});