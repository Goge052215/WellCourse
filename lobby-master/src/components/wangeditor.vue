<template>
  <div>
    <Toolbar
        :editor="editor"
        :defaultConfig="toolbarConfig"
        :mode="mode"
    />
    <Editor
        style="height: 600px;"
        v-model="html"
        :defaultConfig="editorConfig"
        :mode="mode"
        @onCreated="onCreated"
        @onChange="onChange"
    />
  </div>
</template>
<script>
import { upload } from '@/utils/api'
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'

export default {
  name: 'FormWangeditor',
  props: {
    content: {
      type: String,
      default: ''
    },
  },
  components: { Editor, Toolbar },
  data() {
    return {
      dialogVisible: false,
      letexeasy: null,
      editor: null,
      editorNode: null,
      html: '',
      toolbarConfig: {
        insertKeys: {
          index: 99,
          keys: [
            'insertFormulali',
            'editFormulali',
          ],
        },
        excludeKeys: ['group-video', 'emotion']
      },
      editorConfig: {
        placeholder: '请输入',
        hoverbarKeys: {
          formula: {
            menuKeys: ['editFormulali'], // “编辑公式”菜单
          },
        },
        MENU_CONF:{
          uploadImage: {
            server: process.env.VUE_APP_API_BASE_IMG_URL,
            fieldName: 'file',
            async customUpload(file, insertFn) {
              const form = new FormData();
              form.append("file", file);
              upload(form).then(res => {
                insertFn(process.env.VUE_APP_API_BASE_IMG_URL + res.visitUrl, '', '#')
              })
            }
          }
        }
      },
      mode: 'default', // or 'simple'
    }
  },
  watch: {
    content(newVal) {
      this.html = newVal
    },
  },
  mounted() {
  },
  beforeDestroy() {
    const editor = this.editor
    if (editor == null) return
    editor.destroy() // 组件销毁时，及时销毁编辑器
  },
  methods: {
    onCreated(editor) {
      this.editor = Object.seal(editor)
    },
    onChange() {
      this.$emit('change',this.html)
    },
  }
}
</script>
<style lang="less" scoped>
</style>
