function initSingleSelectQuestion(unitQuestion) {
    var questionContent = unitQuestion.questionContent;
    var answerRule = unitQuestion.answerRule;
    var questionId = unitQuestion.questionId;
    var answerOptionId = unitQuestion.answerOptionId;
    var questionGroup = unitQuestion.questionGroup;
    var displayText = "";
    displayText += "<div class=\"question-main\" style=\"margin-top: 2em;\">\n";
    displayText += "<div class=\"exam-question\" perscore=\"10.0\">" + answerOptionId + "、" + questionContent + "</div>\n";
    displayText += "<div class=\"answers\" questionsid=\"\">\n";

    var oneRow = true;
    if(answerRule.length > 2){
        oneRow = false;
    }

    displayText += "<div class=\"single-select\">\n";

    for (var i = 0; i < answerRule.length; i++) {
        var selectSeq = questionId + "-" + i;
        if(!oneRow && i > 0) {
            displayText += "<div class=\"single-select\">\n";
        }
        displayText += "<label for=\"a" + selectSeq + "\">\n";
        displayText += "<div class=\"words\">" + answerRule[i].name + "</div>";
        displayText += "</label>";
        displayText += "<input type=\"radio\" class=\"radioOrCheck hidden\" name=\"q" + questionId + "\" id=\"a" + selectSeq + "\" group=\"" + questionGroup + "\" value=\"" + answerRule[i].score + "\"/>\n";
        if(!oneRow && i < answerRule.length -1) {
            displayText += "</div>";
        }
    }
    displayText += "</div>";

    displayText += "</div></div>";
    return displayText;
}