<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input :placeholder="$t('shop.shopCode')" v-model="listQuery.shopCode" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter"/>
      <el-input :placeholder="$t('shop.shopName')" v-model="listQuery.shopName" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter"/>
      <el-select v-model="listQuery.status" :placeholder="$t('shop.status')" clearable style="width: 120px" class="filter-item">
        <el-option v-for="item in statusOptions" :key="item.value" :label="item.name" :value="item.value"/>
      </el-select>
      <el-select v-model="listQuery.sort" style="width: 140px" class="filter-item" @change="handleFilter">
        <el-option v-for="item in sortOptions" :key="item.key" :label="item.label" :value="item.key"/>
      </el-select>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">{{ $t('table.search') }}</el-button>
    </div>

    <el-table
      v-loading="listLoading"
      :key="tableKey"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;">
      <el-table-column :label="$t('table.id')" type="index" align="center" width="80" />
      <el-table-column :label="$t('shop.shopCode')" min-width="20%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.shopCode }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('shop.shopName')" min-width="20%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.shopName }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('shop.status')" min-width="15%" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status | statusFilter">{{ scope.row.status | statusShowFilter }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('shop.charger')" min-width="20%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.charger }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('shop.chargeTel')" min-width="20%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.chargeTel }}</span>
        </template>
      </el-table-column>

      <el-table-column :label="$t('shop.createTime')" min-width="20%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('table.actions')" align="center" min-width="25%" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-if="scope.row.status=='300'" size="mini" type="success" @click="handleModifyStatus(scope.row,'200')">{{ $t('table.enable') }}
          </el-button>
          <el-button v-if="scope.row.status=='200'" size="mini" @click="handleModifyStatus(scope.row,'300')">{{ $t('table.disable') }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination-container">
      <el-pagination v-show="total>0" :current-page="listQuery.page" :page-sizes="[10,20,30, 50]" :page-size="listQuery.limit" :total="total" background layout="total, sizes, prev, pager, next, jumper" @size-change="handleSizeChange" @current-change="handleCurrentChange"/>
    </div>

  </div>
</template>

<script>
import { fetchShopList } from '@/api/shop'
import waves from '@/directive/waves' // 水波纹指令
import { parseTime } from '@/utils'

export default {
  name: 'ShopManagement',
  directives: {
    waves
  },
  filters: {
    statusFilter(status) {
      const statusMap = {
        200: 'success',
        300: 'info',
        400: 'danger'
      }
      return statusMap[status]
    },
    statusShowFilter(status) {
      const statusShowMap = {
        200: '正常',
        300: '禁用',
        400: '已删除'
      }
      return statusShowMap[status]
    }
  },
  data() {
    return {
      tableKey: 0,
      list: null,
      total: null,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        shopCode: undefined,
        shopName: undefined,
        status: undefined,
        sort: '+id'
      },
      statusOptions: [
        {
          name: '全部',
          value: ''
        },
        {
          name: '正常',
          value: 200
        },
        {
          name: '禁用',
          value: 300
        },
        {
          name: '已删除',
          value: 400
        }],
      sortOptions: [{ label: '序号升序', key: '+id' }, { label: '序号降序', key: '-id' }],
      temp: {
        id: undefined,
        shopCode: '',
        shopName: '',
        status: 200,
        detail: ''

      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: 'Edit',
        create: 'Create'
      },
      dialogPvVisible: false,
      pvData: [],
      rules: {
        type: [{ required: true, message: 'type is required', trigger: 'change' }],
        timestamp: [{ type: 'date', required: true, message: 'timestamp is required', trigger: 'change' }],
        title: [{ required: true, message: 'title is required', trigger: 'blur' }]
      },
      downloadLoading: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      fetchShopList(this.listQuery).then(response => {
        var respData = response.data
        if (respData.status === 200) {
          this.list = respData.data.content
          this.total = respData.data.totalElements
          this.listLoading = false
        }

        // Just to simulate the time of the request
        setTimeout(() => {
          this.listLoading = false
        }, 1.5 * 1000)
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    handleSizeChange(val) {
      this.listQuery.limit = val
      this.getList()
    },
    handleCurrentChange(val) {
      this.listQuery.page = val
      this.getList()
    },
    resetTemp() {
      this.temp = {
        id: undefined,
        shopCode: '',
        shopName: '',
        status: 200,
        detail: ''
      }
    },
    handleModifyStatus(row, status) {
      /* var postData = {
        id: row.id,
        status: status
      }

      modifyStatus(postData).then((response) => {
        console.log('resp', response)
        var data = response.data.data
        if (data.code === 200) {
          this.$message({
            message: '操作成功',
            type: 'success'
          })
          row.status = status
        }
      })*/
    }
  }
}
</script>
