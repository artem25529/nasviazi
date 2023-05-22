$(document).ready(function () {
    $('input[name=advertSystem]').change(function () {
        let val = $(this).val()

        let href = location.href
        let questionSymIdx = href.indexOf('?')

        href = questionSymIdx > -1 ? href.slice(0, questionSymIdx) : href
        location.href = href + `?system=${val}`
    })
})