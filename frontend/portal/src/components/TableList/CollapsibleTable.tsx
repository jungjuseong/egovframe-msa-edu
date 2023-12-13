import Paper from '@mui/material/Paper'
import Table from '@mui/material/Table'
import TableBody from '@mui/material/TableBody'
import TableCell from '@mui/material/TableCell'
import TableContainer from '@mui/material/TableContainer'
import TableHead from '@mui/material/TableHead'
import TableRow from '@mui/material/TableRow'
import {
  GridColDef,
  GridRowData,
  GridRowId,
  GridRowsProp,
} from '@material-ui/data-grid'
import React from 'react'
import CollapseRow from './CollapseRow'
import CustomPagination from './CustomPagination'

// FIXME checkout https://mui.com/components/use-media-query/#migrating-from-withwidth
const withWidth = () => (WrappedComponent) => (props) => <WrappedComponent {...props} width="xs" />;

export interface CollapseColDef
  extends Omit<
    GridColDef,
    | 'sortComparator'
    | 'valueGetter'
    | 'valueParser'
    | 'renderEditCell'
    | 'renderHeader'
  > {}

interface CollapsibleTableProps extends WithWidthProps {
  hideColumns?: boolean
  columns: CollapseColDef[]
  xsColumns: CollapseColDef[]
  rows: GridRowsProp
  rowId?: GridRowId
  renderCollapseRow: (row: GridRowData) => React.ReactNode
  page: number
  first: boolean
  last: boolean
  totalPages: number
  handleChangePage: (
    event: React.MouseEvent<HTMLButtonElement> | null,
    page: number,
  ) => void
}

const CollapsibleTable = (props: CollapsibleTableProps) => {
  const {
    width,
    hideColumns = false,
    columns,
    xsColumns,
    rows,
    rowId = 'id',
    renderCollapseRow,
    page,
    first,
    last,
    totalPages,
    handleChangePage,
  } = props

  return (
    <TableContainer className="collapsible" component={Paper}>
      {rows.length > 0 ? (
        <Table aria-label="collapsible table">
          {!hideColumns && (
            <TableHead>
              <TableRow>
                {width === 'xs'
                  ? xsColumns.map(item => (
                      <TableCell
                        key={`collapse-header-row-${item.field}`}
                        align={item.headerAlign}
                        className={String(item.headerClassName) || ''}
                      >
                        {item.headerName}
                      </TableCell>
                    ))
                  : columns.map(item => (
                      <TableCell
                        key={`collapse-header-row-${item.field}`}
                        align={item.headerAlign}
                        className={String(item.headerClassName) || ''}
                      >
                        {item.headerName}
                      </TableCell>
                    ))}
                <TableCell />
              </TableRow>
            </TableHead>
          )}
          <TableBody>
            {rows.map(row => (
              <CollapseRow
                key={`collapse-body-row-${row[rowId]}`}
                columns={width === 'xs' ? xsColumns : columns}
                row={row}
                rowId={rowId}
                collapseClassName="content"
                collapseColumn={renderCollapseRow(row)}
              />
            ))}
          </TableBody>
        </Table>
      ) : (
        <div className="no-rows">No rows</div>
      )}

      <CustomPagination
        page={page}
        first={first}
        last={last}
        onChangePage={handleChangePage}
        totalPages={totalPages}
      />
    </TableContainer>
  )
}

export default withWidth()(CollapsibleTable)
