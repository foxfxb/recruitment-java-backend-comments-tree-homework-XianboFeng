// function bsStep(i) {
//     $('.step').each(function () {
//         var a, $this = $(this);
//         if (i > $this.find('li').length) {
//             console.log('您输入数值已超过步骤最大数量' + $this.find('li').length + '！！！');
//             a = $this.find('li').length;
//         } else if (i == undefined && $this.data('step') == undefined) {
//             a = 1
//         } else if (i == undefined && $this.data('step') != undefined) {
//             a = $(this).data('step');
//         } else {
//             a = i
//         }
//         $(this).find('li').removeClass('active');
//         $(this).find('li:lt(' + a + ')').addClass('active');
//     })
// }

!function (i) {
    i.fn.step = function (e) {
        var t = this;
        var n = {index: 0, title: [], userName: [], date: []};
        var s = (e = i.extend({}, n, e)).title;
        var d = s.length;
        var p = t.width() / d;
        var names = e.userName;
        var dates = e.date;
        t.index = e.index;
        let a = function () {
            var e = "";
            if(s.length > 0){
                e += '<div class="ui-step-progress">' +
                    '   <ul class="nav nav-pills nav-justified step step-progress" style="display: block;">';
                i.each(s, function (i, t) {
                    var name = names[i] ? names[i] : "";
                    var date = dates[i] ? dates[i] : "";
                    e += '      <li style="width:' + p + '">' +
                        '           <a>' + t + '<span class="caret"></span></a>' +
                        '           <div class="step-user-name">' + name +
                        "           </div>" +
                        '           <div class="step-date">' + date  +
                        "           </div>" +
                        "       </li>"
                });
                e += "  </ul>";
                e += "</div>";
                t.append(e);
                t.toStep(t.index);
            }
        };
        return t.toStep = function (i) {
           t.find("ul.step-progress li").each(function () {
                var a, $this = $(this);
                if (i > $this.length) {
                    console.log('您输入数值已超过步骤最大数量' + $this.find('li').length + '！！！');
                    a = $this.length;
                } else if (i == undefined && $this.data('step') == undefined) {
                    a = 1
                } else if (i == undefined && $this.data('step') != undefined) {
                    a = $(this).data('step');
                } else {
                    a = i;
                }
                $(this).parent().find('li').removeClass('active');
                $(this).parent().find('li:lt(' + a + ')').addClass('active');
            });
        }, t.getIndex = function () {
            return t.index
        }, t.nextStep = function () {
            t.index > d - 2 || (t.index++, t.toStep(t.index))
        }, t.prevStep = function () {
            t.index < 1 || (t.index--, t.toStep(t.index))
        }, a(), this
    }
}(jQuery);