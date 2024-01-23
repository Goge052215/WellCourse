import { t } from '@wangeditor/editor'
export default {
    data() {
        return {
            visible: false,
            editor: '',
            title: '',
            letexeasy: null,
            wangeditor: null
        };
    },
    mounted() {
        if(!this.letexeasy){
            setTimeout(() => {
                this.letexeasy = new window.LatexEasy(document.getElementById('latexeasyIframe'));
                this.letexeasy.on('ready', function () {});
                this.letexeasy.init();
            },2000)
        }
    },
    methods: {
        handleClose() {
            this.letexeasy.call('set.latex', {latex: ''})
            this.visible = false;
        },
        init(editor, wangeditor) {
            this.visible = true;
            this.editor = editor;
            this.wangeditor = wangeditor
            this.title = wangeditor.title;
            if(wangeditor.curValue) {
                this.letexeasy.call('set.latex', {latex: wangeditor.curValue})
            }
        },
        handleOk(){
            let that = this
            that.letexeasy.call('get.latex', {}, function (data) {
                const text = data.latex
                if (!text || text.indexOf('placeholder') != -1) return
                if(that.wangeditor.state == 'add') {
                    that.wangeditor.insertFormula(that.editor, text)
                }
                if(that.wangeditor.state == 'edit') {
                    that.wangeditor.updateFormula(that.editor, text)
                }
                that.handleClose()
            })
        },
    },
    render() {
        return (
            <div class="el-dialog__wrapper" style={{zIndex: this.visible ? 2001 : -999, opacity: this.visible ? 1 : 0}}>
                <div class="el-dialog" style="margin-top: 15vh; width: 800px; z-index: 10">
                    <div class="el-dialog__header">
                        <span class="el-dialog__title">{this.title}</span>
                        <button type="button" class="el-dialog__headerbtn"><i class="el-dialog__close el-icon el-icon-close" on-click={this.handleClose}></i></button>
                    </div>
                    <div class="el-dialog__body">
                        <iframe id="latexeasyIframe" frameBorder="0" style="width:100%;height:350px;border:0;outline:none;"
                                src="https://latexeasy.com/editor"></iframe>
                    </div>
                    <div class="el-dialog__footer">
                        <span class="dialog-footer">
                            <button on-click={this.handleOk} class="form-button"><span>{t('formula.ok')}</span></button>
                        </span>
                    </div>
                </div>
                <div class="v-modal" style="z-index: 1;" on-click={this.handleClose}></div>
            </div>
        );
    }
};