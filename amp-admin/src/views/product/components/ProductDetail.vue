<template>
  <div class="createPost-container">
    <el-form ref="postForm" :model="postForm" :rules="rules" class="form-container">

      <sticky :class-name="'sub-navbar '+postForm.status">
        <CommentDropdown v-model="postForm.comment_disabled" />
        <PlatformDropdown v-model="postForm.platforms" />
        <SourceUrlDropdown v-model="postForm.source_uri" />
        <el-button v-loading="loading" style="margin-left: 10px;" type="success" @click="submitForm">发布
        </el-button>
        <el-button v-loading="loading" type="warning" @click="draftForm">草稿</el-button>
      </sticky>

      <div class="createPost-main-container">
        <el-row>

          <el-col :span="24">

            <div class="postInfo-container">
              <input v-model="postForm.id" type="hidden">

              <el-row>
                <el-form-item style="margin-bottom: 40px;" label-width="85px" label="所属分类">
                  <el-cascader :disabled="isEdit" :options="categoryList" :props="props" v-model="selectedCategory" expand-trigger="hover" @change="handleCategoryChange"/>
                </el-form-item>
              </el-row>

              <el-row>
                <el-form-item style="margin-bottom: 40px;" label-width="85px" label="产品名称:">
                  <el-input v-model="postForm.productName" placeholder="请输入产品名称" />
                </el-form-item>
              </el-row>

              <el-row>
                <el-col :span="8">
                  <el-form-item label-width="80px" label="出厂价格:" class="postInfo-container-item">
                    <input v-model="postForm.originalPrice" :precision="2" type="number" placeholder="请输入内容"> 元
                  </el-form-item>
                </el-col>

                <el-col :span="10">
                  <el-form-item label-width="80px" label="销售价格:" class="postInfo-container-item">
                    <input v-model="postForm.salePrice" :precision="2" type="number" name="salePrice" > 元
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label-width="80px" label="上架时间:" class="postInfo-container-item">
                    <el-date-picker v-model="postForm.onlineDate" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" format="yyyy-MM-dd HH:mm:ss" placeholder="选择上架时间"/>
                  </el-form-item>
                </el-col>

                <el-col :span="10">
                  <el-form-item label-width="80px" label="下架时间:" class="postInfo-container-item">
                    <el-date-picker v-model="postForm.offlineDate" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" format="yyyy-MM-dd HH:mm:ss" placeholder="选择下架时间"/>
                  </el-form-item>
                </el-col>

                <el-col :span="6">
                  <el-form-item label-width="80px" label="产品等级:" class="postInfo-container-item">
                    <el-rate
                      v-model="postForm.star"
                      :max="5"
                      :colors="['#99A9BF', '#F7BA2A', '#FF9900']"
                      :low-threshold="1"
                      :high-threshold="5"
                      style="margin-top:8px;"/>
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row>
                <el-form-item label="商品图片">
                  <el-upload
                    :action="uploadImagePath"
                    :show-file-list="false"
                    :headers="headers"
                    :before-upload="beforeUpload"
                    :on-success="uploadImageUrl"
                    class="avatar-uploader"
                    list-type="picture-card"
                    accept=".jpg,.jpeg,.png,.gif">
                    <img v-if="postForm.productImg" :src="postForm.productImg" class="avatar">
                    <i v-else class="el-icon-plus avatar-uploader-icon"/>
                  </el-upload>
                </el-form-item>
              </el-row>

              <el-form-item label="商品画册">
                <el-upload
                  :action="uploadImagePath"
                  :limit="5"
                  :headers="headers"
                  :file-list="uploadedSliders"
                  :before-upload="beforeUpload"
                  :on-exceed="uploadOverrun"
                  :on-success="handleGalleryUrl"
                  :on-remove="handleRemove"
                  multiple
                  accept=".jpg,.jpeg,.png,.gif"
                  list-type="picture-card">
                  <i class="el-icon-plus"/>
                </el-upload>
              </el-form-item>
            </div>
          </el-col>
        </el-row>

        <div class="editor-container">
          <Tinymce ref="editor" :height="400" v-model="postForm.detail" />
        </div>

      </div>
    </el-form>

  </div>
</template>

<script>

import user from '@/store/modules/user'
import Tinymce from '@/components/Tinymce'
import MDinput from '@/components/MDinput'
import Sticky from '@/components/Sticky' // 粘性header组件
import { validateURL } from '@/utils/validate'
import { dateFormat00, dateFormatEndYear } from '@/utils/dateutil'
import { fetchProductDetail, fetchProductEmpty, updateProduct } from '@/api/product'
import { uploadImagePath } from '@/api/image'
import { getToken } from '@/utils/auth'
import { listByLevel, findParent } from '@/api/category'
import { userSearch } from '@/api/remoteSearch'
import Warning from './Warning'
import { CommentDropdown, PlatformDropdown, SourceUrlDropdown } from './Dropdown'

const defaultForm = {
  sliders: [],
  productImg: '',
  status: 'draft',
  shopCode: user.state.shopCode,
  categoryId: undefined, // 产品类别
  productName: '', // 产品名称
  originalPrice: '', // 出厂价格
  salePrice: '', // 销售价格
  detail: '', // 产品详情
  content_short: '', // 文章摘要
  source_uri: '', // 文章外链
  image_uri: '', // 文章图片
  display_time: undefined, // 前台展示时间
  id: undefined,
  platforms: ['a-platform'],
  comment_disabled: false,
  star: 3,
  onlineDate: dateFormat00(new Date()),
  offlineDate: dateFormatEndYear(new Date())
}

export default {
  name: 'ProductDetail',
  components: { Tinymce, MDinput, Sticky, Warning, CommentDropdown, PlatformDropdown, SourceUrlDropdown },
  props: {
    isEdit: {
      type: Boolean,
      default: false
    }
  },
  data() {
    const validateRequire = (rule, value, callback) => {
      if (value === '') {
        this.$message({
          message: rule.field + '为必传项',
          type: 'error'
        })
        callback(new Error(rule.field + '为必传项'))
      } else {
        callback()
      }
    }
    const validateSourceUri = (rule, value, callback) => {
      if (value) {
        if (validateURL(value)) {
          callback()
        } else {
          this.$message({
            message: '外链url填写不正确',
            type: 'error'
          })
          callback(new Error('外链url填写不正确'))
        }
      } else {
        callback()
      }
    }
    return {
      uploadImagePath,
      uploadedSliders: [],
      categoryId: undefined,
      id: undefined,
      postForm: Object.assign({}, defaultForm),
      loading: false,
      userListOptions: [],
      setMoneyPrefix: '¥ ',
      rules: {
        image_uri: [{ validator: validateRequire }],
        productName: [{ validator: validateRequire }],
        detail: [{ validator: validateRequire }],
        source_uri: [{ validator: validateSourceUri, trigger: 'blur' }]
      },
      categoryList: [],
      selectedCategory: [],
      sliders: [],
      props: {
        label: 'categoryName',
        value: 'id'
      }
    }
  },
  computed: {
    contentShortLength() {
      return this.postForm.content_short.length
    },
    headers() {
      return {
        'X-Token': getToken()
      }
    }
  },
  created() {
    this.getCategoryList()
    console.log('editStatus', this.isEdit)
    if (this.isEdit) {
      const id = this.$route.params && this.$route.params.id
      this.fetchData(id)
    } else {
      this.fetchEmpty()
      // this.postForm = Object.assign({}, defaultForm)
    }
  },
  methods: {
    beforeUpload() {
      const _self = this
      _self.productId = this.postForm.id
    },
    uploadImageUrl: function(response) {
      this.postForm.productImg = response.data + '?imageView2/1/w/146/h/146'
      console.log('uploadImageUrl', response, this.postForm.productImg)
    },
    uploadOverrun: function() {
      this.$message({
        type: 'error',
        message: '上传文件个数超出限制!最多上传5张图片!'
      })
    },
    handleGalleryUrl(response, file, fileList) {
      console.log('upload-gallery succes', response)
      this.postForm.sliders.push(response.data)
    },
    handleRemove: function(file, fileList) {
      for (var i = 0; i < this.postForm.sliders.length; i++) {
       // 这里存在两种情况
       // 1. 如果所删除图片是刚刚上传的图片，那么图片地址是file.response.data.url
       //    此时的file.url虽然存在，但是是本机地址，而不是远程地址。
       // 2. 如果所删除图片是后台返回的已有图片，那么图片地址是file.url
        var url
        if (file.response === undefined) {
          url = file.url
        } else {
          url = file.response.data.url
        }

        if (this.postForm.sliders[i] === url) {
          this.postForm.sliders.splice(i, 1)
        }
       }
    },
    getCategoryList() {
      listByLevel().then(response => {
        this.categoryList = response.data.data
        console.log('categorylist', this.categoryList)
      }).catch(err => {
        console.log(err)
      })
    },
    handleCategoryChange(value) {
      this.postForm.categoryId = value[value.length - 1]
    },
    fetchEmpty() {
      fetchProductEmpty().then(response => {
        this.id = response.data.data.id
        this.postForm.id = response.data.data.id
      }).catch(err => {
        console.log(err)
      })
    },
    fetchData(id) {
      fetchProductDetail(id).then(response => {
        this.postForm = response.data.data
        var images = response.data.data.sliders
        for(var i=0; i<images.length; i++) {
          this.uploadedSliders.push({
            url: images[i].url
          })
        }

        this.postForm.sliders = []
        var categoryId = response.data.data.categoryId

        this.fetchSelectedCategory(categoryId)
      }).catch(err => {
        console.log(err)
      })
    },
    fetchSelectedCategory(categoryId) {
      var params = {
        categoryId: categoryId
      }
      findParent(params).then(response => {
        var parentId = response.data.data.id
        var categories = new Array()
        categories.push(parentId)
        categories.push(categoryId)
        this.selectedCategory = categories
        console.log('categories', this.selectedCategory)
      }).catch(err => {
        console.log(err)
      })
    },
    submitForm() {
      this.postForm.display_time = parseInt(this.display_time / 1000)
      console.log(this.postForm)
      this.$refs.postForm.validate(valid => {
        if (valid) {
          if (!this.postForm.categoryId || this.postForm.categoryId.length === 0) {
            this.$message({
              message: '请选择产品类别',
              type: 'warning'
            })
            return
          }
          if (this.postForm.detail.length === 0 || this.postForm.productName.length === 0) {
            this.$message({
              message: '请填写必要的标题和内容',
              type: 'warning'
            })
            return
          }
          if (!this.postForm.onlineDate || this.postForm.onlineDate.length === 0 || !this.postForm.offlineDate || this.postForm.offlineDate.length === 0) {
            this.$message({
              message: '请填写产品上线日期和下线日期',
              type: 'warning'
            })
            return
          }
          updateProduct(this.postForm).then(response => {
            console.log('updateProduct.response', response)
          }).catch(err => {
            console.log(err)
          })
          this.loading = true
          this.$notify({
            title: '成功',
            message: '发布文章成功',
            type: 'success',
            duration: 2000
          })
          this.postForm.status = 'published'
          this.loading = false
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    draftForm() {
      if (this.postForm.detail.length === 0 || this.postForm.productName.length === 0) {
        this.$message({
          message: '请填写必要的标题和内容',
          type: 'warning'
        })
        return
      }
      this.$message({
        message: '保存成功',
        type: 'success',
        showClose: true,
        duration: 1000
      })
      this.postForm.status = 'draft'
    },
    getRemoteUserList(query) {
      userSearch(query).then(response => {
        if (!response.data.items) return
        this.userListOptions = response.data.items.map(v => v.name)
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
@import "src/styles/mixin.scss";
.createPost-container {
  position: relative;
  .createPost-main-container {
    padding: 40px 45px 20px 50px;
    .postInfo-container {
      position: relative;
      @include clearfix;
      margin-bottom: 10px;
      .postInfo-container-item {
        float: left;
      }
    }
    .editor-container {
      min-height: 500px;
      margin: 0 0 30px;
      .editor-upload-btn-container {
        text-align: right;
        margin-right: 10px;
        .editor-upload-btn {
          display: inline-block;
        }
      }
    }
  }
  .word-counter {
    width: 40px;
    position: absolute;
    right: -10px;
    top: 0px;
  }

}
</style>
