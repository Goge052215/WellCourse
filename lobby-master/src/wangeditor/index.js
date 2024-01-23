
import { t, Boot, DomEditor, i18nChangeLanguage, SlateTransforms, SlateRange } from '@wangeditor/editor'
import formulaModule from '@wangeditor/plugin-formula'
import Vue from 'vue'
import liDialog from './liDialog'
import { PENCIL_SVG, SIGMA_SVG } from './icon-svg'
i18nChangeLanguage('en')

Boot.registerModule(formulaModule)


// import {$, append, on, focus, val} from 'dom7'
// import { nanoid } from 'nanoid'
// $.fn.append = append;
// $.fn.on = on;
// $.fn.focus = focus;
// $.fn.val = val;

// function genDomID() {
//   return `w-e-insert-formula-li-${nanoid()}`
// }
let $ele,$root,ifInit = false
window.onload = () => {
  $ele = document.createElement('div');
  document.body.appendChild($ele);
  $ele.id = "formulaLi";
  document.getElementById('app').appendChild($ele);
  $root = new Vue({ el: '#formulaLi', render: (h) => { return h(liDialog, { ref: 'formulaLi' }) } });
}

// 插入
class InsertFormulaMenuli {
  constructor() {
    this.title = t('formula.insert')
    this.iconSvg = SIGMA_SVG
    this.tag = 'button'
    this.showModal = true
    this.modalWidth = 800
    this.$content = null
    // this.textareaId = genDomID()
    // this.buttonId = genDomID()
  }
  getValue(editor) {
    this.state = 'add'
    $root.$refs.formulaLi.init(editor, this);
    return false
    // return ''
  }
  isActive() {
    return false
  }
  exec() {
    if (ifInit) {
      return;
    }
    ifInit = true;
  }
  isDisabled(editor) {
    const { selection } = editor
    if (selection == null) return true
    if (SlateRange.isExpanded(selection)) return true // 选区非折叠，禁用

    const selectedElems = DomEditor.getSelectedElems(editor)

    const hasVoidElem = selectedElems.some(elem => editor.isVoid(elem))
    if (hasVoidElem) return true // 选中了 void 元素，禁用

    const hasPreElem = selectedElems.some(elem => DomEditor.getNodeType(elem) === 'pre')
    if (hasPreElem) return true // 选中了 pre 原则，禁用

    return false
  }
  // getModalPositionNode() {
  //   return null
  // }
  // getModalContentElem(editor) {
  //   // const { textareaId, buttonId } = this
  //   let that = this
  //   // if (this.$content == null) {
  //     const $container = $('<div></div>')
  //     const inputId = `input-${Math.random().toString(16).slice(-8)}`
  //     const buttonId = `button-${Math.random().toString(16).slice(-8)}`
  //     let letexeasy = null
  //
  //     const $inputContainer = $(`<label class="babel-container">
  //             <iframe id="${inputId}"
  //                     frameborder="0"
  //                     style="width:100%;height:350px;border:0;outline:none;"
  //                     src="https://latexeasy.com/editor"
  //             ></iframe>
  //           </label>`)
  //     const $buttonContainer = $(`<div class="button-container">
  //             <button id="${buttonId}">${t('formula.ok')}</button>
  //           </div>`)
  //     $($container).append($inputContainer).append($buttonContainer)
  //     $buttonContainer.on('click', e => {
  //       e.preventDefault()
  //       letexeasy.call('get.latex', {}, function (data) {
  //         const text = data.latex
  //         if (!text || text.indexOf('placeholder') != -1) return
  //         that.insertFormula(editor, text)
  //       });
  //     })
  //   //   this.$content = $content
  //   // }
  //   setTimeout(() => {
  //     $(`#${inputId}`).focus()
  //     letexeasy = new window.LatexEasy(document.getElementById(inputId));
  //     letexeasy.on('ready', function () {
  //       console.log('LatexEasy.ready');
  //     });
  //     letexeasy.init();
  //   })
  //   return $container[0]
  //   // PS：也可以把 $container 缓存下来，这样不用每次重复创建、重复绑定事件，优化性能
  // }
  insertFormula(editor, value) {
    if (!value) return
    // 还原选区
    editor.restoreSelection()
    if (this.isDisabled(editor)) return
    const formulaElem = {
      type: 'formula',
      value: value,
      children: [{ text: '' }], // void node 需要有一个空 text
    }
    editor.insertNode(formulaElem)
  }
}

const insertFormulaMenuConfli = {
  key: 'insertFormulali', // menu key ，唯一。注册之后，可配置到工具栏
  factory() {
    return new InsertFormulaMenuli()
  },
}

Boot.registerMenu(insertFormulaMenuConfli)

// 编辑
class EditFormulaMenuli {
  constructor() {
    this.title = t('formula.edit')
    this.iconSvg = PENCIL_SVG
    this.tag = 'button'
    this.showModal = true
    this.modalWidth = 800
    this.state = ''
    this.curValue = ''
  }
  getSelectedElem(editor) {
    const node = DomEditor.getSelectedNodeByType(editor, 'formula')
    if (node == null) return null
    return node
  }
  getValue(editor) {
    let data = ''
    const formulaElem = this.getSelectedElem(editor)
    if (formulaElem) {
      data = formulaElem.value || ''
    }
    this.curValue = data
    this.state = 'edit'
    $root.$refs.formulaLi.init(editor, this);
    return false
    // return data
  }
  isActive() {
    return false
  }
  exec() {
    if (ifInit) {
      return;
    }
    ifInit = true;
  }
  isDisabled(editor) {
    const { selection } = editor
    if (selection == null) return true
    if (SlateRange.isExpanded(selection)) return true // 选区非折叠，禁用

    // 未匹配到 formula node 则禁用
    const formulaElem = this.getSelectedElem(editor)
    if (formulaElem == null) return true

    return false
  }
  // getModalPositionNode() {
  //   return null
  // }
  // getModalContentElem(editor) {
  //   let that = this
  //   const $container = $('<div></div>')
  //   const inputId = `input-${Math.random().toString(16).slice(-8)}`
  //   const buttonId = `button-${Math.random().toString(16).slice(-8)}`
  //   let letexeasy = null
  //
  //   const $inputContainer = $(`<label class="babel-container">
  //           <iframe id="${inputId}"
  //                   frameborder="0"
  //                   style="width:100%;height:350px;border:0;outline:none;"
  //                   src="https://latexeasy.com/editor"
  //           ></iframe>
  //         </label>`)
  //   const $buttonContainer = $(`<div class="button-container">
  //           <button id="${buttonId}">${t('formula.ok')}</button>
  //         </div>`)
  //   $($container).append($inputContainer).append($buttonContainer)
  //   $buttonContainer.on('click', e => {
  //     e.preventDefault()
  //     letexeasy.call('get.latex', {}, function (data) {
  //       const text = data.latex
  //       if (!text || text.indexOf('placeholder') != -1 ) return
  //       that.updateFormula(editor, text)
  //     });
  //   })
  //   setTimeout(() => {
  //     $(`#${inputId}`).focus()
  //     letexeasy = new window.LatexEasy(document.getElementById(inputId));
  //     letexeasy.on('ready', function () {
  //       console.log('LatexEasy.ready');
  //       letexeasy.call('set.latex', {latex: that.curValue})
  //     });
  //     letexeasy.init();
  //   })
  //   return $container[0]
  //   // PS：也可以把 $container 缓存下来，这样不用每次重复创建、重复绑定事件，优化性能
  // }
  updateFormula(editor, value) {
    if (!value) return
    // 还原选区
    editor.restoreSelection()
    if (this.isDisabled(editor)) return
    const selectedElem = this.getSelectedElem(editor)
    if (selectedElem == null) return

    const path = DomEditor.findPath(editor, selectedElem)
    const props = {
      type: 'formula',
      value: value,
      children: []
    }
    SlateTransforms.setNodes(editor, props, { at: path })
  }
}

const editFormulaMenuConfli = {
  key: 'editFormulali', // menu key ，唯一。注册之后，可配置到工具栏
  factory() {
    return new EditFormulaMenuli()
  },
}

Boot.registerMenu(editFormulaMenuConfli)

